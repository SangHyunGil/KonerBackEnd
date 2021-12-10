package project.SangHyun.domain.service;

import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.dto.response.member.MemberDeleteResponseDto;
import project.SangHyun.dto.response.member.MemberInfoResponseDto;
import project.SangHyun.dto.response.member.MemberProfileResponseDto;
import project.SangHyun.dto.response.member.MemberUpdateInfoResponseDto;
import project.SangHyun.dto.request.member.MemberUpdateInfoRequestDto;

public interface MemberService {
    MemberInfoResponseDto getMemberInfo(MemberDetails memberDetails);
    MemberProfileResponseDto getProfile(Long memberId);
    MemberUpdateInfoResponseDto updateMemberInfo(Long memberId, MemberUpdateInfoRequestDto requestDto);
    MemberDeleteResponseDto deleteMember(Long memberId);
}
