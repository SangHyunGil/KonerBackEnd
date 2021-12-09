package project.SangHyun.domain.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.*;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.service.Impl.SignServiceImpl;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.dto.request.*;
import project.SangHyun.dto.response.MemberChangePwResponseDto;
import project.SangHyun.dto.response.MemberLoginResponseDto;
import project.SangHyun.dto.response.MemberRegisterResponseDto;
import project.SangHyun.dto.response.TokenResponseDto;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SignServiceIntegrationImplTest {

    @Autowired
    SignServiceImpl signService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    RedisService redisService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    public void 회원가입() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test5", "test5", "테스터5", "컴퓨터공학과");

        //when
        MemberRegisterResponseDto ActualResult = signService.registerMember(requestDto);

        //then
        Assertions.assertEquals("test5", ActualResult.getEmail());
    }

    @Test
    public void 회원가입_이메일중복() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test", "test6", "테스터6", "컴퓨터공학과");

        //when, then
        Assertions.assertThrows(MemberEmailAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }

    @Test
    public void 회원가입_닉네임중복() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("test7", "test7", "승범", "컴퓨터공학과");

        //when, then
        Assertions.assertThrows(MemberNicknameAlreadyExistsException.class, ()->signService.registerMember(requestDto));
    }
    
    @Test
    public void 로그인_인증유저() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test").orElseThrow(MemberNotFoundException::new);
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test", "test");

        //when
        MemberLoginResponseDto ActualResult = signService.loginMember(requestDto);

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getId());
        Assertions.assertEquals(member.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(member.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(member.getDepartment(), ActualResult.getDepartment());
        Assertions.assertEquals(0, ActualResult.getStudyInfos().size());
    }

    @Test
    public void 로그인_인증유저X() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test3", "test3");

        //when, then
        Assertions.assertThrows(EmailNotAuthenticatedException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    public void 로그인_비밀번호틀림() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("test", "wrongPW");

        //when, then
        Assertions.assertThrows(LoginFailureException.class, ()->signService.loginMember(requestDto));
    }

    @Test
    public void 이메일전송_처음로그인() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("test", "VERIFY");

        //when
        String ActualResult = signService.sendEmail(requestDto);

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    public void 이메일전송_비밀번호변경() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("test", "PASSWORD");

        //when
        String ActualResult = signService.sendEmail(requestDto);

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    public void 이메일검증_처음로그인() throws Exception {
        //given
        String authCode = UUID.randomUUID().toString();
        redisService.setDataWithExpiration("VERIFY"+"test3", authCode,60*5L);
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("test3", authCode, "VERIFY");

        //when
        String ActualResult = signService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    public void 이메일검증_비밀번호찾기() throws Exception {
        //given
        String authCode = UUID.randomUUID().toString();
        redisService.setDataWithExpiration("PASSWORD"+"test3", authCode,60*5L);
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("test3", authCode, "PASSWORD");

        //when
        String ActualResult = signService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    public void 이메일실패() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("test", "authCode", "PASSWORD");

        //when, then
        Assertions.assertThrows(InvalidAuthCodeException.class, ()->signService.verify(requestDto));
    }

    @Test
    public void 비밀번호변경() throws Exception {
        //given
        Member member = memberRepository.findByEmail("test").orElseThrow(MemberNotFoundException::new);
        MemberChangePwRequestDto requestDto = new MemberChangePwRequestDto("test", "change");

        //when
        MemberChangePwResponseDto ActualResult = signService.changePassword(requestDto);

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getId());
        Assertions.assertEquals(member.getEmail(), ActualResult.getEmail());
        Assertions.assertTrue(passwordEncoder.matches("change", ActualResult.getPassword()));
    }

    @Test
    public void 토큰재발행() throws Exception {
        //given
        String refreshToken = jwtTokenProvider.createRefreshToken("test");
        redisService.setDataWithExpiration(refreshToken, "test", JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);
        Member member = memberRepository.findByEmail("test").orElseThrow(MemberNotFoundException::new);

        ReIssueRequestDto requestDto = new ReIssueRequestDto(refreshToken);

        //when
        TokenResponseDto ActualResult = signService.reIssue(requestDto);

        //then
        Assertions.assertEquals(member.getId(), ActualResult.getId());
        Assertions.assertEquals(member.getNickname(), ActualResult.getNickname());
        Assertions.assertEquals(member.getEmail(), ActualResult.getEmail());
        Assertions.assertEquals(member.getDepartment(), ActualResult.getDepartment());
        Assertions.assertEquals(0, ActualResult.getStudyInfos().size());
    }

    @Test
    public void 토큰재발행_실패() throws Exception {
        //given
        ReIssueRequestDto requestDto = new ReIssueRequestDto(UUID.randomUUID().toString());

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);

        //when, then
        Assertions.assertThrows(InvalidTokenException.class, ()->signService.reIssue(requestDto));
    }
}