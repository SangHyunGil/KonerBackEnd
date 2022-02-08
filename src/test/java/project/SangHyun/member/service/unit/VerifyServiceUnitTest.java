package project.SangHyun.member.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.SangHyun.common.advice.exception.RedisValueDifferentException;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberEmailAuthRequestDto;
import project.SangHyun.member.dto.request.VerifyEmailRequestDto;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.helper.email.EmailHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.impl.VerifyService;
import project.SangHyun.member.tools.sign.SignFactory;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
public class VerifyServiceUnitTest {

    Member authMember;
    Member notAuthMember;

    @InjectMocks
    VerifyService verifyService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    RedisHelper redisHelper;
    @Mock
    EmailHelper emailHelper;

    @BeforeEach
    public void init() {
        authMember = SignFactory.makeAuthTestMember();
        notAuthMember = SignFactory.makeTestNotAuthMember();
    }

    @Test
    @DisplayName("회원가입 후 인증을 위한 이메일을 전송한다.")
    public void sendMail_register() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.VERIFY);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(notAuthMember));
        willDoNothing().given(redisHelper).store(any(), any(), any());
        willDoNothing().given(emailHelper).sendEmail(any(), any(), any());

        //when
        String ActualResult = verifyService.send(requestDto.getEmail(), requestDto.getRedisKey());

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    @DisplayName("비밀번호 변경을 위한 이메일을 전송한다.")
    public void sendMail_pw() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.PASSWORD);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(authMember));
        willDoNothing().given(redisHelper).store(any(), any(), any());
        willDoNothing().given(emailHelper).sendEmail(any(), any(), any());

        //when
        String ActualResult = verifyService.send(requestDto.getEmail(), requestDto.getRedisKey());

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 메일 검증에 성공한다.")
    public void verifyMail_register() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto(notAuthMember.getEmail(), "authCode", RedisKey.VERIFY);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(notAuthMember));
        given(redisHelper.validate(any(), any())).willReturn(true);
        willDoNothing().given(redisHelper).delete(any());

        //when
        String ActualResult = verifyService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 메일 검증에 실패한다.")
    public void verifyMail_register_fail() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "authCode",RedisKey.VERIFY);

        //mocking
        given(redisHelper.validate(any(), any())).willReturn(false);

        //when, then
        Assertions.assertThrows(RedisValueDifferentException.class, () -> verifyService.verify(requestDto));
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 메일 검증에 성공한다.")
    public void verifyMail_pw() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "authCode", RedisKey.PASSWORD);

        //mocking
        given(redisHelper.validate(any(), any())).willReturn(true);
        willDoNothing().given(redisHelper).delete(any());

        //when
        String ActualResult = verifyService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 메일 검증에 실패한다.")
    public void verifyMail_pw_fail() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "authCode",RedisKey.PASSWORD);

        //mocking
        given(redisHelper.validate(any(), any())).willReturn(false);

        //when, then
        Assertions.assertThrows(RedisValueDifferentException.class, () -> verifyService.verify(requestDto));
    }
}
