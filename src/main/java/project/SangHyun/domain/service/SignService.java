package project.SangHyun.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import project.SangHyun.advice.exception.*;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.dto.*;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Role;
import project.SangHyun.domain.rediskey.RedisKey;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.web.dto.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class SignService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    private final RedisService redisService;
    private final EmailService emailService;

    /**
     * Dto로 들어온 값을 통해 회원가입을 진행
     * @param requestDto
     * @return
     */
    @Transactional
    public MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) {
        validateDuplicated(requestDto.getEmail());
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Member member = memberRepository.save(Member.createMember(requestDto));

        return MemberRegisterResponseDto.createDto(member);
    }

    public void validateDuplicated(String email) {
        if (memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistsException();
    }

    /**
     * 로컬 로그인 구현
     * @param requestDto
     * @return
     */
    @Transactional
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword()))
            throw new LoginFailureException();
        if (member.getRole() == Role.ROLE_NOT_PERMITTED)
            throw new EmailNotAuthenticatedException();

        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());
        redisService.setDataWithExpiration(refreshToken, member.getEmail(), JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);
        return MemberLoginResponseDto.createDto(member, jwtTokenProvider.createToken(requestDto.getEmail()), refreshToken);
    }

    /**
     * 이메일 인증을 위한 이메일 전송
     * @param requestDto
     * @return
     */
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
    @Transactional
    public String verify(VerifyEmailRequestDto requestDto) {
        String key = getKey(requestDto.getEmail(), requestDto.getRedisKey());
        String findAuthCode = redisService.getData(key);
        if (findAuthCode == null || !findAuthCode.equals(requestDto.getAuthCode()))
            throw new InvalidAuthCodeException();

        if (requestDto.getRedisKey().equals("VERIFY")) {
            Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
            member.changeRole(Role.ROLE_MEMBER);
        }
        redisService.deleteData(RedisKey.VERIFY.getKey()+requestDto.getEmail());

        return "이메일 인증이 완료되었습니다.";
    }

    private String getKey(String email, String redisKey) {
        String prefix = redisKey == RedisKey.VERIFY.getKey() ? RedisKey.VERIFY.getKey() : RedisKey.PASSWORD.getKey();
        String key = prefix + email;

        return key;
    }

    /**
     * 비밀번호 변경
     * @param requestDto
     * @return
     */
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
    @Transactional
    public TokenResponseDto reIssue(ReIssueRequestDto requestDto) {
        String redisEmail = redisService.getData(requestDto.getRefreshToken());
        String jwtEmail = jwtTokenProvider.getMemberEmail(requestDto.getRefreshToken());
        if (redisEmail == null || !redisEmail.equals(jwtEmail))
            throw new InvalidRefreshTokenException();

        String accessToken = jwtTokenProvider.createToken(jwtEmail);
        String refreshToken = jwtTokenProvider.createRefreshToken(jwtEmail);
        Member member = memberRepository.findByEmail(jwtEmail).orElseThrow(MemberNotFoundException::new);
        redisService.setDataWithExpiration(refreshToken, member.getEmail(), JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);

        return TokenResponseDto.createDto(member, accessToken, refreshToken);
    }
}
