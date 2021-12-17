package project.SangHyun.config.security.guard;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.SangHyun.config.security.member.MemberDetails;

@Component
public class AuthHelper {

    public boolean isAuthenticated() {
        return getAuthentication() instanceof UsernamePasswordAuthenticationToken &&
                getAuthentication().isAuthenticated();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long extractMemberId() {
        return Long.valueOf(getMemberDetails().getId());
    }

    private MemberDetails getMemberDetails() {
        return (MemberDetails) getAuthentication().getPrincipal();
    }

    public String extractMemberRole() {
       return getMemberDetails().getAuthorities()
                   .stream()
                   .map(auth -> auth.getAuthority())
                   .findFirst()
                   .orElseGet(() -> "Anonymous");
    }
}
