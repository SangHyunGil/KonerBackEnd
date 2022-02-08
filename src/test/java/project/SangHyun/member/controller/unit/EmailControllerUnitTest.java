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
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.controller.EmailController;
import project.SangHyun.member.dto.request.MemberEmailAuthRequestDto;
import project.SangHyun.member.service.EmailService;
import project.SangHyun.member.tools.sign.SignFactory;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmailControllerUnitTest {
    MockMvc mockMvc;
    @InjectMocks
    EmailController emailController;
    @Mock
    EmailService emailService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(emailController).build();
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 검증 메일을 발송한다.")
    public void sendMail_register() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.VERIFY);
        String result = "이메일 전송에 성공하였습니다.";
        SingleResult<String> ExpectResult = SignFactory.makeSingleResult(result);

        //mocking
        given(emailService.send(requestDto.getEmail(), requestDto.getRedisKey())).willReturn(result);
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
        given(emailService.send(requestDto.getEmail(), requestDto.getRedisKey())).willReturn(result);
        given(responseService.getSingleResult(result)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }
}
