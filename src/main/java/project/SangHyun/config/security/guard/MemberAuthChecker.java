package project.SangHyun.config.security.guard;

import lombok.RequiredArgsConstructor;
import project.SangHyun.config.security.guard.strategy.AuthStrategy;

@RequiredArgsConstructor
public class MemberAuthChecker {

    private final AuthHelper authHelper;
    private final AuthStrategy authStrategy;

    public boolean check(Long resourceId) {
        Long accessMemberId = authHelper.extractMemberId();

        // 인증을 받은 유저 중, 웹 관리자이거나 정규 회원이면서 리소스 주인인지 체크
        return authHelper.isAuthenticated() &&
               (authHelper.isAdmin() || authHelper.isRegularMember() && authStrategy.check(accessMemberId, resourceId));
    }
}
