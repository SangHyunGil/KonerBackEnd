package project.SangHyun.member.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.common.advice.exception.*;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberLoginRequestDto;
import project.SangHyun.member.dto.request.MemberRegisterRequestDto;
import project.SangHyun.member.dto.request.TokenRequestDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.impl.SignServiceImpl;
import project.SangHyun.member.tools.sign.SignFactory;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SignServiceIntegrationTest {

    @Autowired
    SignServiceImpl signService;
    @Autowired
    JwtTokenHelper refreshTokenHelper;
    @Autowired
    RedisHelper redisHelper;
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
    @DisplayName("회원 가입을 진행한다.")
    public void register() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeRegisterRequestDto();

        //when
        MemberRegisterResponseDto ActualResult = signService.registerMember(requestDto);

        //then
        Assertions.assertEquals("xptmxm6!", ActualResult.getEmail());
    }

    @Test
    @DisplayName("이메일중복으로 인해 회원가입에 실패한다.")
    public void register_fail1() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeDuplicateEmailRequestDto();

        //when, then
        Assertions.assertThrows(MemberEmailAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }

    @Test
    @DisplayName("닉네임중복으로 인해 회원가입에 실패한다.")
    public void register_fail2() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeDuplicateNicknameRequestDto();

        //when, then
        Assertions.assertThrows(MemberNicknameAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }
    
    @Test
    @DisplayName("인증한 로그인 회원은 로그인에 성공한다.")
    public void login() throws Exception {
        //given
        MemberLoginRequestDto requestDto = SignFactory.makeAuthMemberLoginRequestDto();

        //when
        MemberLoginResponseDto ActualResult = signService.loginMember(requestDto);

        //then
        Assertions.assertEquals(requestDto.getEmail(), ActualResult.getEmail());
    }

    @Test
    @DisplayName("인증을 완료하지 못한 로그인 회원은 로그인에 실패한다.")
    public void login_fail1() throws Exception {
        //given
        MemberLoginRequestDto requestDto = SignFactory.makeNotAuthMemberLoginRequestDto();

        //when, then
        Assertions.assertThrows(EmailNotAuthenticatedException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    @DisplayName("비밀번호를 틀린 회원은 로그인에 실패한다.")
    public void login_fail2() throws Exception {
        //given
        MemberLoginRequestDto requestDto = SignFactory.makeWrongPwMemberLoginRequestDto();

        //when, then
        Assertions.assertThrows(LoginFailureException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    @DisplayName("RefreshToken을 이용해 JWT 토큰을 재발급 받는다.")
    public void reIssue() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String refreshToken = makeRefreshToken(member);
        TokenRequestDto requestDto = SignFactory.makeTokenRequestDto(refreshToken);

        //when
        TokenResponseDto ActualResult = signService.tokenReIssue(requestDto);

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getId());
    }

    private String makeRefreshToken(Member member) {
        String refreshToken = refreshTokenHelper.createToken(member.getEmail());
        redisHelper.store(refreshToken, member.getEmail(), refreshTokenHelper.getValidTime());
        return refreshToken;
    }

    @Test
    @DisplayName("RefreshToken이 유효하지 않아 JWT 토큰 재발급에 실패한다.")
    public void reIssue_fail() throws Exception {
        //given
        TokenRequestDto requestDto = SignFactory.makeTokenRequestDto("refreshToken");

        //when, then
        Assertions.assertThrows(AuthenticationEntryPointException.class, ()->signService.tokenReIssue(requestDto));
    }
}