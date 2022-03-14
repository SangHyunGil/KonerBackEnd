package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.factory.sign.SignFactory;
import project.SangHyun.member.controller.dto.request.EmailAuthRequestDto;
import project.SangHyun.member.controller.dto.request.VerifyRequestDto;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VerifyControllerIntegrationTest extends ControllerIntegrationTest{

    @Test
    @DisplayName("회원가입 후 인증에 대한 검증 메일을 발송한다.")
    public void sendMail_register() throws Exception {
        //given
        EmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.VERIFY);

        //when, then
        mockMvc.perform(post("/sign/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호에 대한 검증 메일을 발송한다.")
    public void sendMail_pw() throws Exception {
        //given
        EmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.PASSWORD);

        //when, then
        mockMvc.perform(post("/sign/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 이메일 검증에 성공한다.")
    public void verifyMail_register() throws Exception {
        //given
        String authCode = makeAuthCode(RedisKey.VERIFY, "xptmxm2!");
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm2!", authCode, RedisKey.VERIFY);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 이메일 검증에 실패한다.")
    public void verifyMail_register_fail() throws Exception {
        //given
        makeAuthCode(RedisKey.VERIFY, "xptmxm2!");
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm2!", "wrongAuthCode", RedisKey.VERIFY);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 이메일 검증에 성공한다.")
    public void verifyMail_pw() throws Exception {
        //given
        String authCode = makeAuthCode(RedisKey.PASSWORD, "xptmxm1!");
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", authCode, RedisKey.PASSWORD);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 이메일 검증에 실패한다.")
    public void verifyMail_pw_fail() throws Exception {
        //given
        makeAuthCode(RedisKey.PASSWORD, "xptmxm1!");
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "wrongAuthCode", RedisKey.PASSWORD);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().is4xxClientError());

    }

    private String makeAuthCode(RedisKey redisKey, String email) {
        String authCode = UUID.randomUUID().toString();
        redisHelper.store(redisKey + email, authCode, 60 * 5L);
        return authCode;
    }
}
