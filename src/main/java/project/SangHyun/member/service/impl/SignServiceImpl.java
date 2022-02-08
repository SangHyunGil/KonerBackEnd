package project.SangHyun.member.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.*;
import project.SangHyun.common.helper.FileStoreHelper;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.dto.request.MemberLoginRequestDto;
import project.SangHyun.member.dto.request.MemberRegisterRequestDto;
import project.SangHyun.member.dto.request.TokenRequestDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.SignService;
import project.SangHyun.member.service.impl.dto.JwtTokens;

import java.io.IOException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final FileStoreHelper fileStoreHelper;
    private final JwtTokenHelper accessTokenHelper;
    private final JwtTokenHelper refreshTokenHelper;
    private final RedisHelper redisHelper;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public MemberRegisterResponseDto registerMember(MemberRegisterRequestDto requestDto) throws IOException {
        validateDuplicated(requestDto.getEmail(), requestDto.getNickname());
        Member member = memberRepository.save(
                requestDto.toEntity(passwordEncoder.encode(requestDto.getPassword()), fileStoreHelper.storeFile(requestDto.getProfileImg()))
        );
        return MemberRegisterResponseDto.create(member);
    }

    @Override
    @Transactional
    public MemberLoginResponseDto loginMember(MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        validateLoginInfo(requestDto, member);
        JwtTokens jwtTokens = makeJwtTokens(member.getEmail());
        return MemberLoginResponseDto.create(member, jwtTokens);
    }

    @Override
    @Transactional
    public TokenResponseDto tokenReIssue(TokenRequestDto requestDto) {
        String email = refreshTokenHelper.extractSubject(requestDto.getRefreshToken());
        redisHelper.validate(requestDto.getRefreshToken(), email);
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        JwtTokens jwtTokens = makeJwtTokens(member.getEmail());
        return TokenResponseDto.create(member, jwtTokens);
    }

    private void validateDuplicated(String email, String nickname) {
        if (memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistsException();
        if (memberRepository.findByNickname(nickname).isPresent())
            throw new MemberNicknameAlreadyExistsException();
    }

    private void validateLoginInfo(MemberLoginRequestDto requestDto, Member member) {
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
