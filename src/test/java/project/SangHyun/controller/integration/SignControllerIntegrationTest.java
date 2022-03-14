package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import project.SangHyun.factory.sign.SignFactory;
import project.SangHyun.member.controller.dto.request.LoginRequestDto;
import project.SangHyun.member.controller.dto.request.MemberRegisterRequestDto;
import project.SangHyun.member.controller.dto.request.TokenRequestDto;
import project.SangHyun.member.domain.Member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignControllerIntegrationTest extends ControllerIntegrationTest{

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
                .andExpect(status().isCreated());
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
                .andExpect(status().is4xxClientError());
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
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("로그인을 진행한다.")
    public void login() throws Exception {
        //given
        LoginRequestDto requestDto = SignFactory.makeAuthMemberLoginRequestDto();

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