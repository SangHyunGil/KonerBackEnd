package project.SangHyun.member.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.TestDB;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberLoginRequestDto;
import project.SangHyun.member.dto.request.MemberRegisterRequestDto;
import project.SangHyun.member.dto.request.TokenRequestDto;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.tools.sign.SignFactory;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    JwtTokenHelper refreshTokenHelper;
    @Autowired
    RedisHelper redisHelper;
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
        MemberRegisterRequestDto requestDto = SignFactory.makeRegisterRequestDto();

        //when, then
        mockMvc.perform(multipart("/sign/register")
                        .file((MockMultipartFile) requestDto.getProfileImg())
                        .param("email", requestDto.getEmail())
                        .param("password", requestDto.getPassword())
                        .param("nickname", requestDto.getNickname())
                        .param("department", String.valueOf(requestDto.getDepartment()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 요구사항에 맞지 않는 요청은 실패한다.")
    public void register_fail() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeNotValidRequestDto();

        //when, then
        mockMvc.perform(multipart("/sign/register")
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("email", requestDto.getEmail())
                        .param("password", requestDto.getPassword())
                        .param("nickname", requestDto.getNickname())
                        .param("department", String.valueOf(requestDto.getDepartment()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("중복이메일이 존재해 회원가입이 실패한다.")
    public void register_fail1() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeDuplicateEmailRequestDto();

        //when, then
        mockMvc.perform(multipart("/sign/register")
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("email", requestDto.getEmail())
                        .param("password", requestDto.getPassword())
                        .param("nickname", requestDto.getNickname())
                        .param("department", String.valueOf(requestDto.getDepartment()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("중복닉네임이 존재해 회원가입이 실패한다.")
    public void register_fail2() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeDuplicateNicknameRequestDto();

        //when, then
        mockMvc.perform(multipart("/sign/register")
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("email", requestDto.getEmail())
                        .param("password", requestDto.getPassword())
                        .param("nickname", requestDto.getNickname())
                        .param("department", String.valueOf(requestDto.getDepartment()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("로그인을 진행한다.")
    public void login() throws Exception {
        //given
        MemberLoginRequestDto requestDto = SignFactory.makeAuthMemberLoginRequestDto();

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
        Member member = SignFactory.makeAuthTestMember();
        String refreshToken = makeRefreshToken(member);
        TokenRequestDto requestDto = SignFactory.makeTokenRequestDto(refreshToken);

        //when, then
        mockMvc.perform(post("/sign/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    private String makeRefreshToken(Member member) {
        String refreshToken = refreshTokenHelper.createToken(member.getEmail());
        redisHelper.store(refreshToken, member.getEmail(), refreshTokenHelper.getValidTime());
        return refreshToken;
    }
}