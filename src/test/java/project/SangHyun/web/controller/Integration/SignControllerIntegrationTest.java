package project.SangHyun.web.controller.Integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
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
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.dto.request.*;

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
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    public void 회원가입() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm4!", "xptmxm4!", "테스터4", "컴퓨터공학부");

        //when, then
        mockMvc.perform(post("/sign/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입_중복이메일() throws Exception {
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
    public void 회원가입_중복닉네임() throws Exception {
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
    public void 이메일전송_처음로그인() throws Exception {
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
    public void 이메일전송_비밀번호변경() throws Exception {
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
    public void 이메일인증_처음로그인() throws Exception {
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
    public void 이메일인증_비밀번호변경() throws Exception {
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
    public void 비밀번호변경() throws Exception {
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
    public void 로그인() throws Exception {
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
    public void 토큰재발행() throws Exception {
        //given
        String refreshToken = jwtTokenProvider.createRefreshToken("xptmxm1!");
        redisService.setDataWithExpiration(refreshToken, "xptmxm1!", JwtTokenProvider.REFRESH_TOKEN_VALID_TIME);

        ReIssueRequestDto requestDto = new ReIssueRequestDto(refreshToken);

        //when, then
        mockMvc.perform(post("/sign/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

}