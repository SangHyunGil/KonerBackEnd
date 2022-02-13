package project.SangHyun.config.security.guard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberGuard {

    private final AuthHelper authHelper;

    public boolean check(Long id) {
        return authHelper.isAuthenticated() && hasAuthority(id);
    }

    private boolean hasAuthority(Long id) {
        return (isAuthMember() && isResourceOwner(id)) || isAdmin();
    }

    private boolean isAuthMember() {
        return authHelper.extractMemberRole().equals("ROLE_MEMBER");
    }

    private boolean isResourceOwner(Long id) {
        return id.equals(authHelper.extractMemberId());
    }

    private boolean isAdmin() {
        return authHelper.extractMemberRole().equals("ROLE_ADMIN");
    }
}
