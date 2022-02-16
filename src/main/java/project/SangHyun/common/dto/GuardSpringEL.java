package project.SangHyun.common.dto;

public class GuardSpringEL {
    public static String isMemberOwner(String memberId) {
        return "@MemberOwner.check(" + memberId + ")";
    }
}
