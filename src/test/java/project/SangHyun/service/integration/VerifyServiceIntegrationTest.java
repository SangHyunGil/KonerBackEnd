package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.RedisValueDifferentException;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.factory.sign.SignFactory;
import project.SangHyun.member.controller.dto.request.EmailAuthRequestDto;
import project.SangHyun.member.controller.dto.request.VerifyRequestDto;

import java.util.UUID;

public class VerifyServiceIntegrationTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("회원가입 후 인증을 위한 검증 메일을 전송한다.")
    public void sendMail_register() throws Exception {
        //given
        EmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.VERIFY);

        //when
        String ActualResult = verifyService.send(requestDto.getEmail(), requestDto.getRedisKey());

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    @DisplayName("비밀번호 변경을 위한 검증 메일을 전송한다.")
    public void sendMail_pw() throws Exception {
        //given
        EmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.PASSWORD);

        //when
        String ActualResult = verifyService.send(requestDto.getEmail(), requestDto.getRedisKey());

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    @DisplayName("회원가입에 대한 메일을 검증에 성공한다.")
    public void verifyMail_register() throws Exception {
        //given
        String authCode = makeAuthCode(RedisKey.VERIFY, "xptmxm2!");
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm2!", authCode, RedisKey.VERIFY);

        //when
        String ActualResult = verifyService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    @DisplayName("회원가입에 대한 메일을 검증에 실패한다.")
    public void verifyMail_register_fail() throws Exception {
        //given
        String authCode = makeAuthCode(RedisKey.VERIFY, "xptmxm2!");
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm2!", "wrongAuthCode", RedisKey.VERIFY);

        //when, then
        Assertions.assertThrows(RedisValueDifferentException.class, () -> verifyService.verify(requestDto));
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 메일 검증에 성공한다.")
    public void verifyMail_pw() throws Exception {
        //given
        String authCode = makeAuthCode(RedisKey.PASSWORD, "xptmxm1!");
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", authCode, RedisKey.PASSWORD);

        //when
        String ActualResult = verifyService.verify(requestDto);

        //then
        Assertions.assertEquals("이메일 인증이 완료되었습니다.", ActualResult);
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 메일 검증에 실패한다.")
    public void verifyMail_pw_fail() throws Exception {
        //given
        String authCode = makeAuthCode(RedisKey.PASSWORD, "xptmxm1!");
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "wrongAuthCode", RedisKey.PASSWORD);

        //when, then
        Assertions.assertThrows(RedisValueDifferentException.class, () -> verifyService.verify(requestDto));
    }

    private String makeAuthCode(RedisKey redisKey, String email) {
        String authCode = UUID.randomUUID().toString();
        redisHelper.store(redisKey + email, authCode, 60 * 5L);
        return authCode;
    }
}
