package project.SangHyun.domain.service;

import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.dto.response.MemberGetInfoResponseDto;
import project.SangHyun.dto.response.MemberUpdateInfoResponseDto;
import project.SangHyun.dto.request.MemberUpdateInfoRequestDto;

public interface MemberService {
    MemberGetInfoResponseDto getMemberInfo(MemberDetails memberDetails);
    MemberGetInfoResponseDto getMemberInfo(Long userId);
    MemberUpdateInfoResponseDto updateMemberInfo(Long userId, MemberUpdateInfoRequestDto requestDto);
}
