package project.SangHyun.domain.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.advice.exception.*;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.service.EmailService;
import project.SangHyun.domain.service.Impl.SignServiceImpl;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.dto.request.*;
import project.SangHyun.dto.response.MemberChangePwResponseDto;
import project.SangHyun.dto.response.MemberLoginResponseDto;
import project.SangHyun.dto.response.MemberRegisterResponseDto;
import project.SangHyun.dto.response.TokenResponseDto;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class SignServiceUnitImplTest {

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
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test", "test", "테스터", "컴퓨터공학과");

        Long memberId = 1L;
        Member member = requestDto.toEntity();
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberRegisterResponseDto ExpectResult = MemberRegisterResponseDto.createDto(member);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());
        given(memberRepository.findByNickname(any())).willReturn(Optional.empty());
        given(passwordEncoder.encode(any())).willReturn("encodedPW");
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
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test", "test", "테스터", "컴퓨터공학과");

        Long memberId = 1L;
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
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test", "test", "테스터", "컴퓨터공학과");

        Long memberId = 1L;
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
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test", "test");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        MemberLoginResponseDto ExpectResult = MemberLoginResponseDto.createDto(member, new ArrayList<>(), "accessToken", "refreshToken");

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(studyJoinRepository.findStudyByMemberId(any())).willReturn(new ArrayList<>());
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(jwtTokenProvider.createAccessToken(any())).willReturn("accessToken");
        given(jwtTokenProvider.createRefreshToken(any())).willReturn("refreshToken");
        willDoNothing().given(redisService).setDataWithExpiration(any(), any(), any());

        //when
        MemberLoginResponseDto ActualResult = signService.loginMember(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
        Assertions.assertEquals(ExpectResult.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(ExpectResult.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(ExpectResult.getDepartment(), ActualResult.getDepartment());
        Assertions.assertEquals(ExpectResult.getStudyInfos().size(), 0);
        Assertions.assertEquals(ExpectResult.getAccessToken(), ActualResult.getAccessToken());
        Assertions.assertEquals(ExpectResult.getRefreshToken(), ActualResult.getRefreshToken());
    }

    @Test
    public void 로그인_인증유저X() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test", "test");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
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
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test", "test");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        //when, then
        Assertions.assertThrows(LoginFailureException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    public void 이메일전송_처음로그인() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("test", "VERIFY");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        willDoNothing().given(redisService).setDataWithExpiration(any(), any(), any());
        willDoNothing().given(emailService).send(any(), any(), any());

        //when
        String ActualResult = signService.sendEmail(requestDto);

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    public void 이메일전송_비밀번호변경() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("test", "PASSWORD");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        willDoNothing().given(redisService).setDataWithExpiration(any(), any(), any());
        willDoNothing().given(emailService).send(any(), any(), any());

        //when
        String ActualResult = signService.sendEmail(requestDto);

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    public void 이메일검증_처음로그인() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("test", "authCode", "VERIFY");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(redisService.getData(any())).willReturn("authCode");
        willDoNothing().given(redisService).deleteData(any());

        //when
        String ActualResult = signService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    public void 이메일검증_비밀번호찾기() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("test", "authCode", "PASSWORD");

        //mocking
        given(redisService.getData(any())).willReturn("authCode");
        willDoNothing().given(redisService).deleteData(any());

        //when
        String ActualResult = signService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    public void 이메일실패() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("test", "authCode", "PASSWORD");

        //mocking
        given(redisService.getData(any())).willReturn("different");

        //when, then
        Assertions.assertThrows(InvalidAuthCodeException.class, ()->signService.verify(requestDto));
    }

    @Test
    public void 비밀번호변경() throws Exception {
        //given
        MemberChangePwRequestDto requestDto = new MemberChangePwRequestDto("test", "change");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(passwordEncoder.encode(any())).willReturn("encodedChangedPW");
        willDoNothing().given(redisService).deleteData(any());

        //when
        MemberChangePwResponseDto ActualResult = signService.changePassword(requestDto);

        //then
        Assertions.assertEquals(1L, ActualResult.getId());
        Assertions.assertEquals("test", ActualResult.getEmail());
        Assertions.assertEquals("encodedChangedPW", ActualResult.getPassword());
    }

    @Test
    public void 토큰재발행() throws Exception {
        //given
        ReIssueRequestDto requestDto = new ReIssueRequestDto("refreshToken");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        TokenResponseDto ExpectResult = TokenResponseDto.createDto(member, new ArrayList<>(), "newAccessToken", "newRefreshToken");

        //mocking
        given(redisService.getData(any())).willReturn("test");
        given(jwtTokenProvider.getMemberEmail(any())).willReturn("test");
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        given(jwtTokenProvider.createAccessToken(any())).willReturn("newAccessToken");
        given(jwtTokenProvider.createRefreshToken(any())).willReturn("newRefreshToken");
        given(studyJoinRepository.findStudyByMemberId(any())).willReturn(new ArrayList<>());

        //when
        TokenResponseDto ActualResult = signService.reIssue(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
        Assertions.assertEquals(ExpectResult.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(ExpectResult.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(ExpectResult.getDepartment(), ActualResult.getDepartment());
        Assertions.assertEquals(ExpectResult.getAccessToken(), ActualResult.getAccessToken());
        Assertions.assertEquals(ExpectResult.getRefreshToken(), ActualResult.getRefreshToken());
        Assertions.assertEquals(ExpectResult.getStudyInfos(), ActualResult.getStudyInfos());
    }

    @Test
    public void 토큰재발행_실패() throws Exception {
        //given
        ReIssueRequestDto requestDto = new ReIssueRequestDto("refreshToken");

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //mocking
        given(redisService.getData(any())).willReturn("wrongToken!!!!");
        given(jwtTokenProvider.getMemberEmail(any())).willReturn("!!!wrongToken");

        //when, then
        Assertions.assertThrows(InvalidTokenException.class, ()->signService.reIssue(requestDto));
    }
}