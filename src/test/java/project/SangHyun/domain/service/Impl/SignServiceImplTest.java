package project.SangHyun.domain.service.Impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.advice.exception.EmailNotAuthenticatedException;
import project.SangHyun.advice.exception.LoginFailureException;
import project.SangHyun.advice.exception.MemberEmailAlreadyExistsException;
import project.SangHyun.advice.exception.MemberNicknameAlreadyExistsException;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.service.EmailService;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.dto.request.MemberLoginRequestDto;
import project.SangHyun.dto.request.MemberRegisterRequestDto;
import project.SangHyun.dto.response.MemberLoginResponseDto;
import project.SangHyun.dto.response.MemberRegisterResponseDto;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class SignServiceImplTest {

    @InjectMocks
    SignServiceImpl signService;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    MemberRepository memberRepository;
    @Mock
    RedisService redisService;
    @Mock
    EmailService emailService;
    @Mock
    StudyJoinRepository studyJoinRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Long memberId = 1L;
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test", "test", "테스터", "컴퓨터공학과");

        Member member = requestDto.toEntity();
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberRegisterResponseDto ExpectResult = MemberRegisterResponseDto.createDto(member);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());
        given(memberRepository.findByNickname(any())).willReturn(Optional.empty());
        given(memberRepository.save(any())).willReturn(member);
        
        //when
        MemberRegisterResponseDto ActualResult = signService.registerMember(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
        Assertions.assertEquals(ExpectResult.getEmail(), ActualResult.getEmail());
    }

    @Test
    public void 회원가입_이메일중복() throws Exception {
        //given
        Long memberId = 1L;
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test", "test", "테스터", "컴퓨터공학과");

        Member member = requestDto.toEntity();
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));

        //when, then
        Assertions.assertThrows(MemberEmailAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }

    @Test
    public void 회원가입_닉네임중복() throws Exception {
        //given
        Long memberId = 1L;
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test", "test", "테스터", "컴퓨터공학과");

        Member member = requestDto.toEntity();
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());
        given(memberRepository.findByNickname(any())).willReturn(Optional.ofNullable(member));

        //when, then
        Assertions.assertThrows(MemberNicknameAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }
    
    @Test
    public void 로그인_인증유저() throws Exception {
        //given
        Long memberId = 1L;
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test", "test");

        Member member = new Member("test", "test", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberLoginResponseDto ExpectResult = new MemberLoginResponseDto(memberId, "test", "테스터", "컴퓨터공학부", new ArrayList<>(), "accessToken", "refreshToken");

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(studyJoinRepository.findStudyByMemberId(any())).willReturn(new ArrayList<>());
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(jwtTokenProvider.createRefreshToken(any())).willReturn("refreshToken");
        given(jwtTokenProvider.createAccessToken(any())).willReturn("accessToken");
        willDoNothing().given(redisService).setDataWithExpiration(any(), any(), any());

        //when
        MemberLoginResponseDto ActualResult = signService.loginMember(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
        Assertions.assertEquals(ExpectResult.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(ExpectResult.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(ExpectResult.getDepartment(), ActualResult.getDepartment());
        Assertions.assertEquals(ExpectResult.getStudyIds().size(), 0);
        Assertions.assertEquals(ExpectResult.getAccessToken(), ActualResult.getAccessToken());
        Assertions.assertEquals(ExpectResult.getRefreshToken(), ActualResult.getRefreshToken());
    }

    @Test
    public void 로그인_인증유저X() throws Exception {
        //given
        Long memberId = 1L;
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test", "test");

        Member member = new Member("test", "test", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        //when, then
        Assertions.assertThrows(EmailNotAuthenticatedException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    public void 로그인_비밀번호틀림() throws Exception {
        //given
        Long memberId = 1L;
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test", "test");

        Member member = new Member("test", "test", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        //when, then
        Assertions.assertThrows(LoginFailureException.class, ()->signService.loginMember(requestDto));
    }



}