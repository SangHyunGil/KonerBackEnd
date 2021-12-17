package project.SangHyun.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.config.security.member.MemberDetailsService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenHelper accessTokenHelper;
    private final MemberDetailsService memberDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = extractToken(request);
        if (token != null && accessTokenHelper.validateToken(token)) {
            setAuthentication(token);
        }
        chain.doFilter(request, response);
    }

    private String extractToken(ServletRequest request) {
        return ((HttpServletRequest)request).getHeader("X-AUTH-TOKEN");
    }

    private void setAuthentication(String token) {
        SecurityContextHolder.getContext().setAuthentication(makeAuthenticationToken(token));
    }

    private UsernamePasswordAuthenticationToken makeAuthenticationToken(String token) {
        String email = accessTokenHelper.extractSubject(token);
        MemberDetails memberDetails = memberDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
    }
}
