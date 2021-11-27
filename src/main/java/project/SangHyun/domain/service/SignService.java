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

        Member member = memberRepository.save(
                Member.builder()
                        .email(requestDto.getEmail())
                        .password(passwordEncoder.encode(requestDto.getPassword()))
                        .nickname(requestDto.getNickname())
                        .role(Role.ROLE_NOT_PERMITTED)
                        .build());

        return MemberRegisterResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
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

        String refreshToken = jwtTokenProvider.createRefreshToken();
        redisService.setDataWithExpiration(RedisKey.REFRESH.getKey()+member.getEmail(), refreshToken, JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);
        return new MemberLoginResponseDto(member.getId(), jwtTokenProvider.createToken(requestDto.getEmail()), refreshToken);
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

        return new MemberChangePwResponseDto(member.getEmail(), member.getPassword());
    }

    /**
     * 토큰 재발행
     * @param requestDto
     * @return
     */
    @Transactional
    public TokenResponseDto reIssue(ReIssueRequestDto requestDto) {
        String findRefreshToken = redisService.getData(RedisKey.REFRESH.getKey()+requestDto.getEmail());
        if (findRefreshToken == null || !findRefreshToken.equals(requestDto.getRefreshToken()))
            throw new InvalidRefreshTokenException();

        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        String accessToken = jwtTokenProvider.createToken(member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        redisService.setDataWithExpiration(RedisKey.REFRESH.getKey()+member.getEmail(), refreshToken, JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);

        return new TokenResponseDto(accessToken, refreshToken);
    }
}
