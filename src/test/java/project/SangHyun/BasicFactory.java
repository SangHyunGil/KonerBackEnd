package project.SangHyun;

import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;

import java.util.List;

public class BasicFactory {
    public static <T> SingleResult<T> makeSingleResult(T responseDto) {
        SingleResult<T> singleResult = new SingleResult<>();
        singleResult.setCode(0); singleResult.setSuccess(true); singleResult.setMsg("성공");
        singleResult.setData(responseDto);
        return singleResult;
    }

    public static <T> MultipleResult<T> makeMultipleResult(List<T> responseDtos) {
        MultipleResult<T> multipleResult = new MultipleResult<>();
        multipleResult.setCode(0); multipleResult.setSuccess(true); multipleResult.setMsg("성공");
        multipleResult.setData(responseDtos);
        return multipleResult;
    }

    public static Member makeTestMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "상현", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }

    public static Member makeNotAuthTestMember() {
        Long memberId = 1L;
        Member member = new Member("xptmxm2!", "encodedPW", "유나", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);
        return member;
    }
}
