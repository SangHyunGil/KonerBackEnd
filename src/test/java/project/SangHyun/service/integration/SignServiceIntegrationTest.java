package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.*;
import project.SangHyun.factory.sign.SignFactory;
import project.SangHyun.member.controller.dto.request.LoginRequestDto;
import project.SangHyun.member.controller.dto.request.TokenRequestDto;
import project.SangHyun.member.controller.dto.response.LoginResponseDto;
import project.SangHyun.member.controller.dto.response.TokenResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.service.dto.request.MemberRegisterDto;
import project.SangHyun.member.service.dto.response.MemberDto;

class SignServiceIntegrationTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("회원 가입을 진행한다.")
    public void register() throws Exception {
        //given
        MemberRegisterDto requestDto = SignFactory.makeRegisterDto();

        //when
        MemberDto ActualResult = signService.registerMember(requestDto);

        //then
        Assertions.assertEquals("xptmxm6!", ActualResult.getEmail());
    }

    @Test
    @DisplayName("이메일중복으로 인해 회원가입에 실패한다.")
    public void register_fail1() throws Exception {
        //given
        MemberRegisterDto requestDto = SignFactory.makeDuplicateEmailDto();

        //when, then
        Assertions.assertThrows(MemberEmailAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }

    @Test
    @DisplayName("닉네임중복으로 인해 회원가입에 실패한다.")
    public void register_fail2() throws Exception {
        //given
        MemberRegisterDto requestDto = SignFactory.makeDuplicateNicknameDto();

        //when, then
        Assertions.assertThrows(MemberNicknameAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }
    
    @Test
    @DisplayName("인증한 로그인 회원은 로그인에 성공한다.")
    public void login() throws Exception {
        //given
        LoginRequestDto requestDto = SignFactory.makeAuthMemberLoginRequestDto();

        //when
        LoginResponseDto ActualResult = signService.loginMember(requestDto);

        //then
        Assertions.assertEquals(requestDto.getEmail(), ActualResult.getEmail());
    }

    @Test
    @DisplayName("인증을 완료하지 못한 로그인 회원은 로그인에 실패한다.")
    public void login_fail1() throws Exception {
        //given
        LoginRequestDto requestDto = SignFactory.makeNotAuthMemberLoginRequestDto();

        //when, then
        Assertions.assertThrows(EmailNotAuthenticatedException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    @DisplayName("비밀번호를 틀린 회원은 로그인에 실패한다.")
    public void login_fail2() throws Exception {
        //given
        LoginRequestDto requestDto = SignFactory.makeWrongPwMemberLoginRequestDto();

        //when, then
        Assertions.assertThrows(LoginFailureException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    @DisplayName("RefreshToken을 이용해 JWT 토큰을 재발급 받는다.")
    public void reIssue() throws Exception {
        //given
        String refreshToken = makeRefreshToken(studyMember);
        TokenRequestDto requestDto = SignFactory.makeTokenRequestDto(refreshToken);

        //when
        TokenResponseDto ActualResult = signService.tokenReIssue(requestDto);

        //then
        Assertions.assertEquals(studyMember.getId(), ActualResult.getId());
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