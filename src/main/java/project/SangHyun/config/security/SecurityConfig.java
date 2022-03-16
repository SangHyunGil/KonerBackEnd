package project.SangHyun.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.SangHyun.config.jwt.JwtAuthenticationFilter;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.config.security.accessDeniedHandler.CustomAccessDeniedHandler;
import project.SangHyun.config.security.authenticationEntryPoint.CustomAuthenticationEntryPoint;
import project.SangHyun.config.security.member.MemberDetailsService;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenHelper accessTokenHelper;
    private final MemberDetailsService memberDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .httpBasic().disable() // rest api이므로 기본설정 미사용
                .cors()
            .and()
                .csrf().disable() // rest api이므로 csrf 보안 미사용
                .headers()
                    .frameOptions()
                    .sameOrigin()
            .and()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt로 인증하므로 세션 미사용
            .and()
                .authorizeRequests()
                .antMatchers("/api/subscribe").permitAll()
                .antMatchers("/api/sign/**").permitAll()
                .antMatchers("/api/social/**").permitAll()
                .antMatchers("/api/exception/**").permitAll()
                .antMatchers("/api/confirm-email").permitAll()
                .antMatchers("/api/ws-stomp/**").permitAll()
                .antMatchers("/api/pub/**").permitAll()
                .antMatchers("/api/sub/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/rooms").permitAll()
                .antMatchers(HttpMethod.GET, "/api/studies").permitAll()
                .antMatchers(HttpMethod.GET, "/api/studies/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/studies/*/members").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/{memberId}").permitAll()

                .antMatchers("/api/users/password").permitAll()

                .antMatchers(HttpMethod.PUT, "/api/users/{userId}").access("@MemberOwner.check(#userId)")
                .antMatchers(HttpMethod.DELETE, "/api/users/{userId}").access("@MemberOwner.check(#userId)")

                .antMatchers(HttpMethod.PUT, "/api/studies/{studyId}").access("@StudyCreator.check(#studyId)")
                .antMatchers(HttpMethod.DELETE, "/api/studies/{studyId}").access("@StudyCreator.check(#studyId)")

                .antMatchers(HttpMethod.PUT, "/api/studies/{studyId}/joins/{memberId}").access("@StudyCreatorOrAdmin.check(#studyId)")
                .antMatchers(HttpMethod.DELETE, "/api/studies/{studyId}/joins/{memberId}").access("@StudyCreatorOrAdmin.check(#studyId)")

                .antMatchers(HttpMethod.PUT, "/api/studies/{studyId}/authorities/{memberId}").access("@StudyCreator.check(#studyId)")

                .antMatchers(HttpMethod.POST, "/api/studies/{studyId}/boards/*").access("@StudyCreatorOrAdmin.check(#studyId)")
                .antMatchers(HttpMethod.PUT, "/api/studies/{studyId}/boards/*").access("@StudyCreatorOrAdmin.check(#studyId)")
                .antMatchers(HttpMethod.DELETE, "/api/studies/{studyId}/boards/*").access("@StudyCreatorOrAdmin.check(#studyId)")

                .antMatchers(HttpMethod.GET, "/api/studies/{studyId}/boards/*/articles").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.POST, "/api/studies/{studyId}/boards/*/articles").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.GET, "/api/studies/{studyId}/boards/*/articles/*").access("@StudyMember.check(#studyId)")

                .antMatchers(HttpMethod.PUT, "/api/studies/{studyId}/boards/*/articles/{articleId}").access("@StudyArticleOwner.check(#studyId, #articleId)")
                .antMatchers(HttpMethod.DELETE, "/api/studies/{studyId}/boards/*/articles/{articleId}").access("@StudyArticleOwner.check(#studyId, #articleId)")

                .antMatchers(HttpMethod.GET, "/api/studies/{studyId}/boards/*/articles/*/comments").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.POST, "/api/studies/{studyId}/boards/*/articles/*/comments").access("@StudyMember.check(#studyId)")

                .antMatchers(HttpMethod.PUT, "/api/studies/{studyId}/boards/*/articles/*/comments/{commentId}").access("@StudyCommentOwner.check(#studyId, #commentId)")
                .antMatchers(HttpMethod.DELETE, "/api/studies/{studyId}/boards/*/articles/*/comments/{commentId}").access("@StudyCommentOwner.check(#studyId, #commentId)")

                .antMatchers("/api/studies/{studyId}/schedules/**").access("@StudyMember.check(#studyId)")

                .antMatchers(HttpMethod.GET, "/api/studies/{studyId}/videorooms").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.POST, "/api/studies/{studyId}/videorooms").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.PUT, "/api/studies/{studyId}/videorooms/{videoRoomId}").access("@VideoRoomOwner.check(#studyId, #videoRoomId)")
                .antMatchers(HttpMethod.DELETE, "/api/studies/{studyId}/videorooms/{videoRoomId}").access("@VideoRoomOwner.check(#studyId, #videoRoomId)")


                .anyRequest().hasAnyRole("MEMBER", "ADMIN")

            .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(accessTokenHelper, memberDetailsService), UsernamePasswordAuthenticationFilter.class); // jwt 필터 추가
    }
}
