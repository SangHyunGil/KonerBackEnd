package project.SangHyun.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.enums.MemberRole;

public class MemberTest {
    @Test
    @DisplayName("회원의 정보를 수정한다.")
    public void updateMember() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member("test", "test", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto("test", "닉네임 수정", "컴퓨터공학부");

        //when
        Member updateMember = member.updateMemberInfo(requestDto);

        //then
        Assertions.assertEquals(requestDto.getNickname(), updateMember.getNickname());
    }

    @Test
    @DisplayName("회원의 권한을 수정한다.")
    public void changeRole() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member("test", "test", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //when
        member.changeRole(MemberRole.ROLE_MEMBER);

        //then
        Assertions.assertEquals(MemberRole.ROLE_MEMBER, member.getMemberRole());
    }

    @Test
    @DisplayName("회원의 비밀번호를 수정한다.")
    public void changePassword() throws Exception {
        //given
        Long memberId = 1L;
        Member member = new Member("test", "test", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //when
        member.changePassword("changed");

        //then
        Assertions.assertEquals("changed", member.getPassword());
    }
}
