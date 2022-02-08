package project.SangHyun.member.service;

import project.SangHyun.member.dto.request.MemberLoginRequestDto;
import project.SangHyun.member.dto.request.MemberRegisterRequestDto;
import project.SangHyun.member.dto.request.TokenRequestDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;

import java.io.IOException;

public interface SignService {
    MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) throws IOException;
    MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto);
    TokenResponseDto tokenReIssue(TokenRequestDto requestDto);
}
