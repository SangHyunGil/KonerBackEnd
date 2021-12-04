package project.SangHyun.domain.service;

import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.dto.response.MemberInfoResponseDto;
import project.SangHyun.dto.response.MemberProfileResponseDto;
import project.SangHyun.dto.response.MemberUpdateInfoResponseDto;
import project.SangHyun.dto.request.MemberUpdateInfoRequestDto;

public interface MemberService {
    MemberInfoResponseDto getMemberInfo(MemberDetails memberDetails);
    MemberProfileResponseDto getProfile(Long userId);
    MemberUpdateInfoResponseDto updateMemberInfo(Long userId, MemberUpdateInfoRequestDto requestDto);
}
