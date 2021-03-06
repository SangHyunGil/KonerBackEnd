package project.SangHyun.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.*;
import project.SangHyun.helper.AwsS3BucketHelper;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.controller.dto.request.LoginRequestDto;
import project.SangHyun.member.controller.dto.request.TokenRequestDto;
import project.SangHyun.member.controller.dto.response.LoginResponseDto;
import project.SangHyun.member.controller.dto.response.TokenResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.dto.JwtTokens;
import project.SangHyun.member.service.dto.response.MemberDto;
import project.SangHyun.member.service.dto.request.MemberRegisterDto;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignService {

    private final AwsS3BucketHelper fileStoreHelper;
    private final JwtTokenHelper accessTokenHelper;
    private final JwtTokenHelper refreshTokenHelper;
    private final RedisHelper redisHelper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto registerMember(MemberRegisterDto requestDto) throws IOException {
        validateDuplicated(requestDto.getEmail(), requestDto.getNickname());
        Member member = memberRepository.save(
                requestDto.toEntity(passwordEncoder.encode(requestDto.getPassword()), fileStoreHelper.store(requestDto.getProfileImg()))
        );
        return MemberDto.create(member);
    }

    public LoginResponseDto loginMember(LoginRequestDto requestDto) {
        Member member = findMemberByEmail(requestDto.getEmail());
        validateLoginInfo(requestDto, member);
        JwtTokens jwtTokens = makeJwtTokens(member.getEmail());
        return LoginResponseDto.create(member, jwtTokens);
    }

    @Transactional
    public TokenResponseDto tokenReIssue(TokenRequestDto requestDto) {
        String email = refreshTokenHelper.extractSubject(requestDto.getRefreshToken());
        redisHelper.validate(requestDto.getRefreshToken(), email);
        Member member = findMemberByEmail(email);
        JwtTokens jwtTokens = makeJwtTokens(member.getEmail());
        return TokenResponseDto.create(member, jwtTokens);
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    private void validateDuplicated(String email, String nickname) {
        if (memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistsException();
        if (memberRepository.findByNickname(nickname).isPresent())
            throw new MemberNicknameAlreadyExistsException();
    }

    private void validateLoginInfo(LoginRequestDto requestDto, Member member) {
        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword()))
            throw new LoginFailureException();
        if (member.getMemberRole() == MemberRole.ROLE_NOT_PERMITTED)
            throw new EmailNotAuthenticatedException();
    }

    private JwtTokens makeJwtTokens(String subject) {
        String accessToken = accessTokenHelper.createToken(subject);
        String refreshToken = refreshTokenHelper.createToken(subject);
        redisHelper.store(refreshToken, subject, refreshTokenHelper.getValidTime());
        return new JwtTokens(accessToken, refreshToken);
    }
}
