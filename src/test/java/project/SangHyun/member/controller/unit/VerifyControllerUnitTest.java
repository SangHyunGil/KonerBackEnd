package project.SangHyun.member.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.common.advice.ExceptionAdvice;
import project.SangHyun.common.advice.exception.RedisValueDifferentException;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.controller.VerifyController;
import project.SangHyun.member.dto.request.MemberEmailAuthRequestDto;
import project.SangHyun.member.dto.request.VerifyRequestDto;
import project.SangHyun.member.service.VerifyService;
import project.SangHyun.member.tools.sign.SignFactory;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class VerifyControllerUnitTest {
    MockMvc mockMvc;
    @InjectMocks
    VerifyController verifyController;
    @Mock
    VerifyService verifyService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(verifyController)
                .setControllerAdvice(new ExceptionAdvice(new ResponseServiceImpl()))
                .build();
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 검증 메일을 발송한다.")
    public void sendMail_register() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.VERIFY);
        String result = "이메일 전송에 성공하였습니다.";
        SingleResult<String> ExpectResult = SignFactory.makeSingleResult(result);

        //mocking
        given(verifyService.send(requestDto.getEmail(), requestDto.getRedisKey())).willReturn(result);
        given(responseService.getSingleResult(result)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호에 대한 검증 메일을 발송한다.")
    public void sendMail_pw() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.PASSWORD);
        String result = "이메일 전송에 성공하였습니다.";
        SingleResult<String> ExpectResult = SignFactory.makeSingleResult(result);

        //mocking
        given(verifyService.send(requestDto.getEmail(), requestDto.getRedisKey())).willReturn(result);
        given(responseService.getSingleResult(result)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 이메일 검증에 성공한다.")
    public void verifyMail_register() throws Exception {
        //given
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "authCode", RedisKey.VERIFY);
        String result = "이메일 인증이 완료되었습니다.";
        SingleResult<String> ExpectResult = SignFactory.makeSingleResult(result);

        //mocking
        given(verifyService.verify(requestDto)).willReturn(result);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 이메일 검증에 실패한다.")
    public void verifyMail_register_fail() throws Exception {
        //given
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "wrongAuthCode", RedisKey.VERIFY);
        String result = "이메일 인증이 완료되었습니다.";
        SingleResult<String> ExpectResult = SignFactory.makeSingleResult(result);

        //mocking
        given(verifyService.verify(requestDto)).willThrow(RedisValueDifferentException.class);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 이메일 검증을 성공한다.")
    public void verifyMail_pw() throws Exception {
        //given
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "authCode", RedisKey.PASSWORD);
        String result = "이메일 인증이 완료되었습니다.";
        SingleResult<String> ExpectResult = SignFactory.makeSingleResult(result);

        //mocking
        given(verifyService.verify(requestDto)).willReturn(result);
        given(responseService.getSingleResult(result)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 이메일 검증에 실패한다.")
    public void verifyMail_pw_fail() throws Exception {
        //given
        VerifyRequestDto requestDto = SignFactory.makeVerifyEmailRequestDto("xptmxm1!", "wrongAuthCode", RedisKey.PASSWORD);
        String result = "이메일 인증이 완료되었습니다.";
        SingleResult<String> ExpectResult = SignFactory.makeSingleResult(result);

        //mocking
        given(verifyService.verify(requestDto)).willThrow(RedisValueDifferentException.class);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().is5xxServerError());
    }
}
