package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import project.SangHyun.advice.exception.*;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.impl.dto.StudyInfoDto;
import project.SangHyun.domain.service.EmailService;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.domain.service.SignService;
import project.SangHyun.dto.request.member.*;
import project.SangHyun.dto.response.member.MemberChangePwResponseDto;
import project.SangHyun.dto.response.member.MemberLoginResponseDto;
import project.SangHyun.dto.response.member.MemberRegisterResponseDto;
import project.SangHyun.dto.response.member.TokenResponseDto;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final StudyJoinRepository studyJoinRepository;

    private final RedisService redisService;
    private final EmailService emailService;

    /**
     * Dto로 들어온 값을 통해 회원가입을 진행
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) {
        validateDuplicated(requestDto.getEmail(), requestDto.getNickname());
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member member = memberRepository.save(requestDto.toEntity());

        return MemberRegisterResponseDto.createDto(member);
    }

    private void validateDuplicated(String email, String nickname) {
        if (memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistsException();
        if (memberRepository.findByNickname(nickname).isPresent())
            throw new MemberNicknameAlreadyExistsException();
    }

    /**
     * 로컬 로그인 구현
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        validateLoginInfo(requestDto, member);

        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());
        redisService.setDataWithExpiration(refreshToken, member.getEmail(), JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);
        List<StudyInfoDto> studyInfoDtos = studyJoinRepository.findStudyInfoByMemberId(member.getId());

        return MemberLoginResponseDto.createDto(member, studyInfoDtos, jwtTokenProvider.createAccessToken(requestDto.getEmail()), refreshToken);
    }

    private void validateLoginInfo(MemberLoginRequestDto requestDto, Member member) {
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword()))
            throw new LoginFailureException();
        if (member.getMemberRole() == MemberRole.ROLE_NOT_PERMITTED)
            throw new EmailNotAuthenticatedException();
    }

    /**
     * 이메일 인증을 위한 이메일 전송
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public String sendEmail(MemberEmailAuthRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);

        String key = getKey(requestDto.getEmail(), requestDto.getRedisKey());
        String authCode = UUID.randomUUID().toString();
        redisService.setDataWithExpiration(key, authCode, 60 * 5L);
        emailService.send(member.getEmail(), authCode, requestDto.getRedisKey());

        return "이메일 전송에 성공하였습니다.";
    }

    /**
     * 이메일 인증
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public String verify(VerifyEmailRequestDto requestDto) {
        String key = getKey(requestDto.getEmail(), requestDto.getRedisKey());
        String findAuthCode = redisService.getData(key);

        if (findAuthCode == null || !findAuthCode.equals(requestDto.getAuthCode()))
            throw new InvalidAuthCodeException();

        if (requestDto.getRedisKey().equals("VERIFY")) {
            Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
            member.changeRole(MemberRole.ROLE_MEMBER);
        }
        redisService.deleteData(RedisKey.VERIFY.getKey()+requestDto.getEmail());

        return "이메일 인증이 완료되었습니다.";
    }

    private String getKey(String email, String redisKey) {
        String prefix = redisKey.equals(RedisKey.VERIFY.getKey()) ? RedisKey.VERIFY.getKey() : RedisKey.PASSWORD.getKey();
        String key = prefix + email;

        return key;
    }

    /**
     * 비밀번호 변경
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public MemberChangePwResponseDto changePassword(MemberChangePwRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        member.changePassword(passwordEncoder.encode(requestDto.getPassword()));
        redisService.deleteData(RedisKey.PASSWORD.getKey()+requestDto.getEmail());

        return MemberChangePwResponseDto.createDto(member);
    }

    /**
     * 토큰 재발행
     * @param requestDto
     * @return
     */
    @Override
    @Transactional
    public TokenResponseDto reIssue(ReIssueRequestDto requestDto) {
        String redisEmail = redisService.getData(requestDto.getRefreshToken());
        String jwtEmail = jwtTokenProvider.getMemberEmail(requestDto.getRefreshToken());
        if (redisEmail == null || !redisEmail.equals(jwtEmail))
            throw new InvalidTokenException();

        Member member = memberRepository.findByEmail(jwtEmail).orElseThrow(MemberNotFoundException::new);

        String accessToken = jwtTokenProvider.createAccessToken(jwtEmail);
        String refreshToken = jwtTokenProvider.createRefreshToken(jwtEmail);
        redisService.setDataWithExpiration(refreshToken, member.getEmail(), JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);
        List<StudyInfoDto> studyInfoDtos = studyJoinRepository.findStudyInfoByMemberId(member.getId());

        return TokenResponseDto.createDto(member, studyInfoDtos, accessToken, refreshToken);
    }
}
