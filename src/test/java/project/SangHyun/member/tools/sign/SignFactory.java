package project.SangHyun.member.tools.sign;

import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.*;
import project.SangHyun.member.dto.response.MemberChangePwResponseDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.service.impl.JwtTokens;

import java.util.ArrayList;

public class SignFactory extends BasicFactory {

    // Request
    public static MemberRegisterRequestDto makeRegisterRequestDto() {
        return new MemberRegisterRequestDto("xptmxm6!", "xptmxm6!", "테스터6", "컴퓨터공학과");
    }

    public static MemberRegisterRequestDto makeDuplicateEmailRequestDto() {
        return new MemberRegisterRequestDto("xptmxm1!", "xptmxm6!", "테스터6", "컴퓨터공학과");
    }

    public static MemberRegisterRequestDto makeDuplicateNicknameRequestDto() {
        return new MemberRegisterRequestDto("xptmxm6!", "xptmxm6!", "승범", "컴퓨터공학과");
    }

    public static MemberLoginRequestDto makeAuthMemberLoginRequestDto() {
        return new MemberLoginRequestDto("xptmxm1!", "xptmxm1!");
    }

    public static MemberLoginRequestDto makeNotAuthMemberLoginRequestDto() {
        return new MemberLoginRequestDto("xptmxm2!", "xptmxm2!");
    }

    public static MemberLoginRequestDto makeWrongPwMemberLoginRequestDto() {
        return new MemberLoginRequestDto("xptmxm1!", "wrong");
    }

    public static MemberEmailAuthRequestDto makeEmailAuthRequestDto(String redisKey) {
        return new MemberEmailAuthRequestDto("xptmxm1!", redisKey);
    }

    public static VerifyEmailRequestDto makeVerifyEmailRequestDto(String email, String authCode, String redisKey) {
        return new VerifyEmailRequestDto(email, authCode, redisKey);
    }

    public static MemberChangePwRequestDto makeChangePwRequestDto(String email, String password) {
        return new MemberChangePwRequestDto(email, password);
    }

    public static TokenRequestDto makeTokenRequestDto(String refreshToken) {
        return new TokenRequestDto(refreshToken);
    }

    public static Member makeAuthTestMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "승범", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    // Response
    public static MemberRegisterResponseDto makeRegisterResponseDto(Member member) {
        return MemberRegisterResponseDto.create(member);
    }

    public static MemberLoginResponseDto makeLoginResponseDto(Member member) {
        return MemberLoginResponseDto.create(member, new ArrayList<>(), new JwtTokens("accessToken", "refreshToken"));
    }

    public static TokenResponseDto makeTokenResponseDto(Member member) {
        return TokenResponseDto.create(member, new ArrayList<>(), new JwtTokens("newAccessToken", "newRefreshToken"));
    }

    public static MemberChangePwResponseDto makeChangePwResponseDto(Member member) {
        return MemberChangePwResponseDto.create(member);
    }
}
