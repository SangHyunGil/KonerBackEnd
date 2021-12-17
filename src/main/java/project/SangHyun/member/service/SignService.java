package project.SangHyun.member.service;

import project.SangHyun.member.dto.request.*;
import project.SangHyun.member.dto.response.MemberChangePwResponseDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;

public interface SignService {
    MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto);
    MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto);
    String sendEmail(MemberEmailAuthRequestDto requestDto);
    String verify(VerifyEmailRequestDto requestDto);
    MemberChangePwResponseDto changePassword(MemberChangePwRequestDto requestDto);
    TokenResponseDto tokenReIssue(TokenRequestDto requestDto);
}
