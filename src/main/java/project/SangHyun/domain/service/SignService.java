package project.SangHyun.domain.service;

import project.SangHyun.dto.request.*;
import project.SangHyun.dto.response.MemberChangePwResponseDto;
import project.SangHyun.dto.response.MemberLoginResponseDto;
import project.SangHyun.dto.response.MemberRegisterResponseDto;
import project.SangHyun.dto.response.TokenResponseDto;

public interface SignService {
    MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto);
    MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto);
    String sendEmail(MemberEmailAuthRequestDto requestDto);
    String verify(VerifyEmailRequestDto requestDto);
    MemberChangePwResponseDto changePassword(MemberChangePwRequestDto requestDto);
    TokenResponseDto reIssue(ReIssueRequestDto requestDto);
}
