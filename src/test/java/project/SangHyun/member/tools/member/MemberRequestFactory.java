package project.SangHyun.member.tools.member;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.enums.MemberRole;

import java.util.Collections;

public class MemberRequestFactory {
    public static MemberDetails makeMemberDetails() {
        return new MemberDetails(1L,"xptmxm1!", "encodedPW",
                Collections.singletonList(new SimpleGrantedAuthority(MemberRole.ROLE_MEMBER.toString())));
    }

    public static MemberUpdateRequestDto makeUpdateRequestDto(String nickname) {
        return new MemberUpdateRequestDto("xptmxm1!", nickname, "컴퓨터공학부");
    }

    public static Member makeTestMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "상현", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }
}
