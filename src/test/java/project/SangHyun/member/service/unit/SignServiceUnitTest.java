package project.SangHyun.member.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.SangHyun.common.advice.exception.*;
import project.SangHyun.common.helper.FileStoreHelper;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberLoginRequestDto;
import project.SangHyun.member.dto.request.MemberRegisterRequestDto;
import project.SangHyun.member.dto.request.TokenRequestDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.impl.SignServiceImpl;
import project.SangHyun.member.tools.sign.SignFactory;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SignServiceUnitTest {
    Member authMember;
    Member notAuthMember;

    @InjectMocks
    SignServiceImpl signService;
    @Mock
    JwtTokenHelper accessTokenHelper;
    @Mock
    JwtTokenHelper refreshTokenHelper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    MemberRepository memberRepository;
    @Mock
    RedisHelper redisHelper;
    @Mock
    FileStoreHelper fileStoreHelper;

    @BeforeEach
    public void init() {
        signService = new SignServiceImpl(accessTokenHelper, refreshTokenHelper, fileStoreHelper, redisHelper, passwordEncoder, memberRepository);

        authMember = SignFactory.makeAuthTestMember();
        notAuthMember = SignFactory.makeTestNotAuthMember();
    }

    @Test
    @DisplayName("회원 가입을 진행한다.")
    public void register() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeRegisterRequestDto();
        Member createdMember = requestDto.toEntity("encoded PW", null);
        MemberRegisterResponseDto ExpectResult = SignFactory.makeRegisterResponseDto(createdMember);

        //mocking
        given(passwordEncoder.encode(any())).willReturn("encoded PW");
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());
        given(memberRepository.findByNickname(any())).willReturn(Optional.empty());
        given(passwordEncoder.encode(any())).willReturn("encodedPW");
        given(memberRepository.save(any())).willReturn(createdMember);
        given(fileStoreHelper.storeFile(requestDto.getProfileImg())).willReturn("C:\\Users\\Family\\Pictures\\Screenshots\\1.png");
        
        //when
        MemberRegisterResponseDto ActualResult = signService.registerMember(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getEmail(), ActualResult.getEmail());
    }

    @Test
    @DisplayName("이메일중복으로 인해 회원 가입에 실패한다.")
    public void register_fail1() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeRegisterRequestDto();

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(notAuthMember));

        //when, then
        Assertions.assertThrows(MemberEmailAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }

    @Test
    @DisplayName("닉네임중복으로 인해 회원 가입에 실패한다.")
    public void register_fail2() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeRegisterRequestDto();

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());
        given(memberRepository.findByNickname(any())).willReturn(Optional.ofNullable(notAuthMember));

        //when, then
        Assertions.assertThrows(MemberNicknameAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }
    
    @Test
    @DisplayName("로그인을 진행한다.")
    public void login() throws Exception {
        //given
        MemberLoginRequestDto requestDto = SignFactory.makeAuthMemberLoginRequestDto();
        MemberLoginResponseDto ExpectResult = SignFactory.makeLoginResponseDto(authMember);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(authMember));
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(accessTokenHelper.createToken(any())).willReturn("accessToken");
        given(refreshTokenHelper.createToken(any())).willReturn("refreshToken");
        willDoNothing().given(redisHelper).store(any(), any(), any());

        //when
        MemberLoginResponseDto ActualResult = signService.loginMember(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("인증이 미완료된 회원은 로그인에 실패한다.")
    public void login_fail1() throws Exception {
        //given
        MemberLoginRequestDto requestDto = SignFactory.makeAuthMemberLoginRequestDto();

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(notAuthMember));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        //when, then
        Assertions.assertThrows(EmailNotAuthenticatedException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    @DisplayName("비밀번호가 틀린 회원은 로그인에 실패한다.")
    public void login_fail2() throws Exception {
        //given
        MemberLoginRequestDto requestDto = SignFactory.makeAuthMemberLoginRequestDto();

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(authMember));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        //when, then
        Assertions.assertThrows(LoginFailureException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    @DisplayName("RefreshToken을 이용해 토큰을 재발행한다.")
    public void reIssue() throws Exception {
        //given
        TokenRequestDto requestDto = SignFactory.makeTokenRequestDto("refreshToken");
        TokenResponseDto ExpectResult = SignFactory.makeTokenResponseDto(authMember);

        //mocking
        given(refreshTokenHelper.extractSubject(any())).willReturn("test");
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(authMember));
        given(accessTokenHelper.createToken(any())).willReturn("newAccessToken");
        given(refreshTokenHelper.createToken(any())).willReturn("newRefreshToken");
        willDoNothing().given(redisHelper).validate(any(), any());

        //when
        TokenResponseDto ActualResult = signService.tokenReIssue(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("잘못된 RefreshToken에 의해 토큰 재발행에 실패한다.")
    public void reIssue_fail() throws Exception {
        //given
        TokenRequestDto requestDto = SignFactory.makeTokenRequestDto("refreshToken");

        //mocking
        given(refreshTokenHelper.extractSubject(any())).willReturn("!!!wrongToken");
        willThrow(RedisValueDifferentException.class).willDoNothing().given(redisHelper).validate(any(), any());

        //when, then
        Assertions.assertThrows(RedisValueDifferentException.class, ()->signService.tokenReIssue(requestDto));
    }
}