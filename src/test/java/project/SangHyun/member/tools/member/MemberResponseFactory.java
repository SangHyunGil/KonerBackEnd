package project.SangHyun.member.tools.member;

import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;

import java.util.List;

public class MemberResponseFactory {
    public static MemberInfoResponseDto makeInfoResponseDto(Member member) {
        return MemberInfoResponseDto.create(member, List.of());
    }

    public static MemberProfileResponseDto makeProfileResponseDto(Member member) {
        return MemberProfileResponseDto.create(member);
    }

    public static MemberUpdateResponseDto makeUpdateResponseDto(Member member) {
        return MemberUpdateResponseDto.create(member);
    }

    public static MemberDeleteResponseDto makeDeleteResponseDto(Member member) {
        return MemberDeleteResponseDto.create(member);
    }
}
