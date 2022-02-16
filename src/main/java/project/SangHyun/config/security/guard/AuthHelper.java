package project.SangHyun.config.security.guard;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.SangHyun.config.security.member.MemberDetails;

@Component
public class AuthHelper {

    public boolean isRegularMember() {
        return extractMemberRole().equals("ROLE_MEMBER");
    }

    public Boolean isAdmin() {
        return extractMemberRole().equals("ROLE_ADMIN");
    }

    public Long extractMemberId() {
        return Long.valueOf(getMemberDetails().getId());
    }

    public boolean isAuthenticated() {
        return getAuthentication() instanceof UsernamePasswordAuthenticationToken &&
                getAuthentication().isAuthenticated();
    }

    private String extractMemberRole() {
       return getMemberDetails().getAuthorities()
                   .stream()
                   .map(auth -> auth.getAuthority())
                   .findFirst()
                   .orElseGet(() -> "Anonymous");
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private MemberDetails getMemberDetails() {
        return (MemberDetails) getAuthentication().getPrincipal();
    }
}
