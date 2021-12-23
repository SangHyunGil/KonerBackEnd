package project.SangHyun.member.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.*;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.*;
import project.SangHyun.member.dto.response.MemberChangePwResponseDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.SignService;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyjoin.repository.impl.StudyInfoDto;
import project.SangHyun.utils.service.EmailService;
import project.SangHyun.utils.service.RedisService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final JwtTokenHelper accessTokenHelper;
    private final JwtTokenHelper refreshTokenHelper;

    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final StudyJoinRepository studyJoinRepository;

    private final RedisService redisService;
    private final EmailService emailService;

    @Override
    @Transactional
    public MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) {
        validateDuplicated(requestDto.getEmail(), requestDto.getNickname());
        Member member = memberRepository.save(requestDto.toEntity(passwordEncoder));
        return MemberRegisterResponseDto.create(member);
    }

    private void validateDuplicated(String email, String nickname) {
        if (memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistsException();
        if (memberRepository.findByNickname(nickname).isPresent())
            throw new MemberNicknameAlreadyExistsException();
    }

    @Override
    @Transactional
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        validateLoginInfo(requestDto, member);
        List<StudyInfoDto> studyInfos = studyJoinRepository.findStudyInfoByMemberId(member.getId());
        JwtTokens jwtTokens = makeJwtTokens(member.getEmail());
        return MemberLoginResponseDto.create(member, studyInfos, jwtTokens);
    }

    private void validateLoginInfo(MemberLoginRequestDto requestDto, Member member) {
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword()))
            throw new LoginFailureException();
        if (member.getMemberRole() == MemberRole.ROLE_NOT_PERMITTED)
            throw new EmailNotAuthenticatedException();
    }

    private JwtTokens makeJwtTokens(String email) {
        String accessToken = accessTokenHelper.createToken(email);
        String refreshToken = refreshTokenHelper.createToken(email);
        redisService.setDataWithExpiration(refreshToken, email, refreshTokenHelper.getValidTime());
        return new JwtTokens(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public TokenResponseDto tokenReIssue(TokenRequestDto requestDto) {
        String email = refreshTokenHelper.extractSubject(requestDto.getRefreshToken());
        compareWithRedisValue(requestDto.getRefreshToken(), email);
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<StudyInfoDto> studyInfos = studyJoinRepository.findStudyInfoByMemberId(member.getId());
        JwtTokens jwtTokens = makeJwtTokens(member.getEmail());
        return TokenResponseDto.create(member, studyInfos, jwtTokens);
    }

    // 요청이 실사용자에 의한 것인지 검증하기 위해 Redis에 저장된 값과 비교
    private void compareWithRedisValue(String redisKey, String expectValue) {
        String redisValue = redisService.getData(redisKey);
        if (redisValue == null || !redisValue.equals(expectValue))
            throw new RedisValueDifferentException();
    }

    @Override
    @Transactional
    public String sendEmail(MemberEmailAuthRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        String authCode = makeAuthCode(requestDto);
        emailService.send(member.getEmail(), authCode, requestDto.getRedisKey());

        return "이메일 전송에 성공하였습니다.";
    }

    private String makeAuthCode(MemberEmailAuthRequestDto requestDto) {
        String key = getRedisKey(requestDto.getEmail(), requestDto.getRedisKey());
        String authCode = UUID.randomUUID().toString();
        redisService.setDataWithExpiration(key, authCode, 60 * 5L);
        return authCode;
    }

    private String getRedisKey(String email, String redisKey) {
        String prefix = redisKey.equals(RedisKey.VERIFY.getKey()) ? RedisKey.VERIFY.getKey() : RedisKey.PASSWORD.getKey();
        String key = prefix + email;

        return key;
    }

    @Override
    @Transactional
    public String verify(VerifyEmailRequestDto requestDto) {
        String redisKey = getRedisKey(requestDto.getEmail(), requestDto.getRedisKey());
        compareWithRedisValue(redisKey, requestDto.getAuthCode());
        if (isVerifyEmail(requestDto)) {
            Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
            member.changeRole(MemberRole.ROLE_MEMBER);
        }
        redisService.deleteData(RedisKey.VERIFY.getKey()+requestDto.getEmail());

        return "이메일 인증이 완료되었습니다.";
    }

    private boolean isVerifyEmail(VerifyEmailRequestDto requestDto) {
        return requestDto.getRedisKey().equals("VERIFY");
    }

    @Override
    @Transactional
    public MemberChangePwResponseDto changePassword(MemberChangePwRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        member.changePassword(passwordEncoder.encode(requestDto.getPassword()));
        redisService.deleteData(RedisKey.PASSWORD.getKey()+requestDto.getEmail());

        return MemberChangePwResponseDto.create(member);
    }
}