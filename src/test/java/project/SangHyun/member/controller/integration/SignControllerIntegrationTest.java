package project.SangHyun.member.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.TestDB;
import project.SangHyun.config.jwt.JwtTokenHandler;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.utils.service.RedisService;
import project.SangHyun.member.dto.request.*;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class SignControllerIntegrationTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RedisService redisService;
    @Autowired
    JwtTokenHelper refreshTokenHelper;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    @DisplayName("회원가입을 진행한다.")
    public void register_success() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm6!", "xptmxm6!", "테스터4", "컴퓨터공학부");

        //when, then
        mockMvc.perform(post("/sign/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("중복이메일이 존재해 회원가입이 실패한다.")
    public void register_fail1() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm1!", "xptmxm1!", "테스터3", "컴퓨터공학부");

        //when, then
        mockMvc.perform(post("/sign/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("중복닉네임이 존재해 회원가입이 실패한다.")
    public void register_fail2() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm1!", "xptmxm1!", "테스터", "컴퓨터공학부");

        //when, then
        mockMvc.perform(post("/sign/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 검증 메일을 발송한다.")
    public void sendMail_register() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("xptmxm2!", "VERIFY");

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
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("xptmxm1!", "PASSWORD");

        //when, then
        mockMvc.perform(post("/sign/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 이메일을 검증한다.")
    public void verifyMail_register() throws Exception {
        //given
        String authCode = UUID.randomUUID().toString();
        redisService.setDataWithExpiration("VERIFY"+"xptmxm2!", authCode, 60 * 5L);
        redisService.getData("VERIFY"+"xptmxm2!");
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("xptmxm2!", authCode, "VERIFY");

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 이메일을 검증한다.")
    public void verifyMail_pw() throws Exception {
        //given
        String authCode = UUID.randomUUID().toString();
        redisService.setDataWithExpiration("PASSWORD"+"xptmxm1!", authCode, 60 * 5L);

        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("xptmxm1!", authCode, "PASSWORD");

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());

    }
    
    @Test
    @DisplayName("비밀번호 변경을 진행한다.")
    public void changePW() throws Exception {
        //given
        MemberChangePwRequestDto requestDto = new MemberChangePwRequestDto("xptmxm1!", "xptmxm1!changePassword");

        //when, then
        mockMvc.perform(post("/sign/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인을 진행한다.")
    public void login() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("xptmxm1!", "xptmxm1!");

        //when, then
        mockMvc.perform(post("/sign/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("RefreshToken을 이용해 JWT 토큰들을 재발급한다.")
    public void reIssue() throws Exception {
        //given
        String refreshToken = refreshTokenHelper.createToken("xptmxm1!");
        redisService.setDataWithExpiration(refreshToken, "xptmxm1!", refreshTokenHelper.getValidTime());

        TokenRequestDto requestDto = new TokenRequestDto(refreshToken);

        //when, then
        mockMvc.perform(post("/sign/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

}