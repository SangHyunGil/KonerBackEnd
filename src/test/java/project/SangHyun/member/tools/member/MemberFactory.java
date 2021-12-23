package project.SangHyun.member.tools.member;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.BasicFactory;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;
import project.SangHyun.member.enums.MemberRole;

import java.util.Collections;
import java.util.List;

public class MemberFactory extends BasicFactory {
    public static MemberDetails makeMemberDetails() {
        return new MemberDetails(1L,"xptmxm1!", "encodedPW",
                Collections.singletonList(new SimpleGrantedAuthority(MemberRole.ROLE_MEMBER.toString())));
    }

    public static MemberUpdateRequestDto makeUpdateRequestDto(String nickname) {
        return new MemberUpdateRequestDto("xptmxm1!", nickname, "컴퓨터공학부");
    }

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
