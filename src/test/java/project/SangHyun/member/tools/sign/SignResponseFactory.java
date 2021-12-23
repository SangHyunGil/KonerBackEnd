package project.SangHyun.member.tools.sign;

import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.response.*;
import project.SangHyun.member.service.impl.JwtTokens;

import java.util.ArrayList;
import java.util.List;

public class SignResponseFactory {
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
