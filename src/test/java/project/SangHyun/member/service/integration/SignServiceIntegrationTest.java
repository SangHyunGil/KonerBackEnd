package project.SangHyun.member.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.*;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.impl.SignServiceImpl;
import project.SangHyun.utils.service.RedisService;
import project.SangHyun.member.dto.response.MemberChangePwResponseDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.dto.request.*;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SignServiceIntegrationTest {

    @Autowired
    SignServiceImpl signService;
    @Autowired
    JwtTokenHelper refreshTokenHelper;
    @Autowired
    RedisService redisService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("회원 정보를 로드한다.")
    public void register() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm6!", "xptmxm6!", "테스터6", "컴퓨터공학과");

        //when
        MemberRegisterResponseDto ActualResult = signService.registerMember(requestDto);

        //then
        Assertions.assertEquals("xptmxm6!", ActualResult.getEmail());
    }

    @Test
    @DisplayName("이메일중복으로 인해 회원가입에 실패한다.")
    public void register_fail1() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm1!", "xptmxm6!", "테스터6", "컴퓨터공학과");

        //when, then
        Assertions.assertThrows(MemberEmailAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }

    @Test
    @DisplayName("닉네임중복으로 인해 회원가입에 실패한다.")
    public void register_fail2() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm7!", "xptmxm7!", "승범", "컴퓨터공학과");

        //when, then
        Assertions.assertThrows(MemberNicknameAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }
    
    @Test
    @DisplayName("인증한 로그인 회원은 로그인에 성공한다.")
    public void login() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("xptmxm1!", "xptmxm1!");

        //when
        MemberLoginResponseDto ActualResult = signService.loginMember(requestDto);

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getId());
        Assertions.assertEquals(member.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(member.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(member.getDepartment(), ActualResult.getDepartment());
        Assertions.assertEquals(0, ActualResult.getStudyInfos().size());
    }

    @Test
    @DisplayName("인증을 완료하지 못한 로그인 회원은 로그인에 실패한다.")
    public void login_fail1() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("xptmxm2!", "xptmxm2!");

        //when, then
        Assertions.assertThrows(EmailNotAuthenticatedException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    @DisplayName("비밀번호를 틀린 회원은 로그인에 실패한다.")
    public void login_fail2() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("xptmxm1!", "wrongPW");

        //when, then
        Assertions.assertThrows(LoginFailureException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    @DisplayName("회원가입 후 인증을 위한 검증 메일을 전송한다.")
    public void sendMail_register() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("xptmxm1!", "VERIFY");

        //when
        String ActualResult = signService.sendEmail(requestDto);

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    @DisplayName("비밀번호 변경을 위한 검증 메일을 전송한다.")
    public void sendMail_pw() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("xptmxm1!", "PASSWORD");

        //when
        String ActualResult = signService.sendEmail(requestDto);

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    @DisplayName("회원가입에 대한 메일을 검증한다.")
    public void verifyMail_register() throws Exception {
        //given
        String authCode = UUID.randomUUID().toString();
        redisService.setDataWithExpiration("VERIFY"+"xptmxm2!", authCode,60*5L);
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("xptmxm2!", authCode, "VERIFY");

        //when
        String ActualResult = signService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 메일을 검증한다.")
    public void verifyMail_pw() throws Exception {
        //given
        String authCode = UUID.randomUUID().toString();
        redisService.setDataWithExpiration("PASSWORD"+"xptmxm1!", authCode,60*5L);
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("xptmxm1!", authCode, "PASSWORD");

        //when
        String ActualResult = signService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    @DisplayName("Redis에 저장된 값과 달라 이메일 검증에 실패한다.")
    public void verfiyMail_fail() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("xptmxm1!", "authCode", "PASSWORD");

        //when, then
        Assertions.assertThrows(RedisValueDifferentException.class, ()->signService.verify(requestDto));
    }

    @Test
    @DisplayName("비밀번호를 변경한다.")
    public void changePW() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        MemberChangePwRequestDto requestDto = new MemberChangePwRequestDto("xptmxm1!", "change");

        //when
        MemberChangePwResponseDto ActualResult = signService.changePassword(requestDto);

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getId());
        Assertions.assertEquals(member.getEmail(), ActualResult.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("change", ActualResult.getPassword()));
    }

    @Test
    @DisplayName("RefreshToken을 이용해 JWT 토큰을 재발급 받는다.")
    public void reIssue() throws Exception {
        //given
        String refreshToken = refreshTokenHelper.createToken("xptmxm1!");
        redisService.setDataWithExpiration(refreshToken, "xptmxm1!", refreshTokenHelper.getValidTime());
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);

        TokenRequestDto requestDto = new TokenRequestDto(refreshToken);

        //when
        TokenResponseDto ActualResult = signService.tokenReIssue(requestDto);

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getId());
        Assertions.assertEquals(member.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(member.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(member.getDepartment(), ActualResult.getDepartment());
        Assertions.assertEquals(0, ActualResult.getStudyInfos().size());
    }

    @Test
    @DisplayName("RefreshToken이 유효하지 않아 JWT 토큰 재발급에 실패한다.")
    public void reIssue_fail() throws Exception {
        //given
        TokenRequestDto requestDto = new TokenRequestDto(UUID.randomUUID().toString());

        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //when, then
        Assertions.assertThrows(AuthenticationEntryPointException.class, ()->signService.tokenReIssue(requestDto));
    }
}