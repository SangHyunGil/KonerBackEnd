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
                .antMatchers("/subscribe").permitAll()
                .antMatchers("/sign/**").permitAll()
                .antMatchers("/social/**").permitAll()
                .antMatchers("/exception/**").permitAll()
                .antMatchers("/confirm-email").permitAll()
                .antMatchers("/ws-stomp/**").permitAll()
                .antMatchers("/pub/**").permitAll()
                .antMatchers("/sub/**").permitAll()
                .antMatchers(HttpMethod.GET, "/room").permitAll()
                .antMatchers(HttpMethod.GET, "/study").permitAll()
                .antMatchers(HttpMethod.GET, "/study/*").permitAll()
                .antMatchers(HttpMethod.GET, "/study/*/member").permitAll()
                .antMatchers(HttpMethod.GET, "/users/{memberId}").permitAll()

                .antMatchers("/users/password").permitAll()

                .antMatchers(HttpMethod.PUT, "/users/{userId}").access("@MemberOwner.check(#userId)")
                .antMatchers(HttpMethod.DELETE, "/users/{userId}").access("@MemberOwner.check(#userId)")

                .antMatchers(HttpMethod.PUT, "/study/{studyId}").access("@StudyCreator.check(#studyId)")
                .antMatchers(HttpMethod.DELETE, "/study/{studyId}").access("@StudyCreator.check(#studyId)")

                .antMatchers(HttpMethod.PUT, "/study/{studyId}/join/{memberId}").access("@StudyCreatorOrAdmin.check(#studyId)")
                .antMatchers(HttpMethod.DELETE, "/study/{studyId}/join/{memberId}").access("@StudyCreatorOrAdmin.check(#studyId)")

                .antMatchers(HttpMethod.POST, "/study/{studyId}/board/*").access("@StudyCreatorOrAdmin.check(#studyId)")
                .antMatchers(HttpMethod.PUT, "/study/{studyId}/board/*").access("@StudyCreatorOrAdmin.check(#studyId)")
                .antMatchers(HttpMethod.DELETE, "/study/{studyId}/board/*").access("@StudyCreatorOrAdmin.check(#studyId)")

                .antMatchers(HttpMethod.GET, "/study/{studyId}/board/*/article").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.POST, "/study/{studyId}/board/*/article").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.GET, "/study/{studyId}/board/*/article/*").access("@StudyMember.check(#studyId)")

                .antMatchers(HttpMethod.PUT, "/study/{studyId}/board/*/article/{articleId}").access("@StudyArticleOwner.check(#studyId, #articleId)")
                .antMatchers(HttpMethod.DELETE, "/study/{studyId}/board/*/article/{articleId}").access("@StudyArticleOwner.check(#studyId, #articleId)")

                .antMatchers(HttpMethod.GET, "/study/{studyId}/board/*/article/*/comment").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.POST, "/study/{studyId}/board/*/article/*/comment").access("@StudyMember.check(#studyId)")

                .antMatchers(HttpMethod.PUT, "/study/{studyId}/board/*/article/*/comment/{commentId}").access("@StudyCommentOwner.check(#studyId, #commentId)")
                .antMatchers(HttpMethod.DELETE, "/study/{studyId}/board/*/article/*/comment/{commentId}").access("@StudyCommentOwner.check(#studyId, #commentId)")

                .antMatchers("/study/{studyId}/schedule/**").access("@StudyMember.check(#studyId)")

                .antMatchers(HttpMethod.GET, "/study/{studyId}/videoroom").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.POST, "/study/{studyId}/videoroom").access("@StudyMember.check(#studyId)")
                .antMatchers(HttpMethod.PUT, "/study/{studyId}/videoroom/{videoRoomId}").access("@VideoRoomOwner.check(#studyId, #videoRoomId)")
                .antMatchers(HttpMethod.DELETE, "/study/{studyId}/videoroom/{videoRoomId}").access("@VideoRoomOwner.check(#studyId, #videoRoomId)")


                .anyRequest().hasAnyRole("MEMBER", "ADMIN")

            .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(accessTokenHelper, memberDetailsService), UsernamePasswordAuthenticationFilter.class); // jwt 필터 추가
    }
}
