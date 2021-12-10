package project.SangHyun.domain.service;

import project.SangHyun.dto.request.member.*;
import project.SangHyun.dto.response.member.MemberChangePwResponseDto;
import project.SangHyun.dto.response.member.MemberLoginResponseDto;
import project.SangHyun.dto.response.member.MemberRegisterResponseDto;
import project.SangHyun.dto.response.member.TokenResponseDto;

public interface SignService {
    MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto);
    MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto);
    String sendEmail(MemberEmailAuthRequestDto requestDto);
    String verify(VerifyEmailRequestDto requestDto);
    MemberChangePwResponseDto changePassword(MemberChangePwRequestDto requestDto);
    TokenResponseDto reIssue(ReIssueRequestDto requestDto);
}
