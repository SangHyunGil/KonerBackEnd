package project.SangHyun.member.service;

import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;

import java.io.IOException;

public interface MemberService {
    MemberInfoResponseDto getMemberInfo(MemberDetails memberDetails);
    MemberProfileResponseDto getProfile(Long memberId);
    MemberUpdateResponseDto updateMember(Long memberId, MemberUpdateRequestDto requestDto) throws IOException;
    MemberDeleteResponseDto deleteMember(Long memberId);
}
