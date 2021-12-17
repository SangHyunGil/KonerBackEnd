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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;
import project.SangHyun.member.service.impl.JwtTokens;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.member.service.SignService;
import project.SangHyun.member.dto.response.MemberChangePwResponseDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.dto.request.*;
import project.SangHyun.member.controller.SignController;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SignControllerTest {
    MockMvc mockMvc;
    @InjectMocks
    SignController signController;
    @Mock
    SignService signService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(signController).build();
    }

    @Test
    @DisplayName("회원가입을 진행한다.")
    public void register() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm1!", "xptmxm1!", "테스터", "컴퓨터공학부");

        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "xptmxm1!encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);
        MemberRegisterResponseDto responseDto = MemberRegisterResponseDto.create(member);

        SingleResult<MemberRegisterResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(signService.registerMember(requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 검증 메일을 발송한다.")
    public void sendMail_register() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("test", "VERIFY");

        String result = "이메일 전송에 성공하였습니다.";

        SingleResult<String> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(result);

        //mocking
        given(signService.sendEmail(requestDto)).willReturn(result);
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
        MemberEmailAuthRequestDto requestDto = new MemberEmailAuthRequestDto("test", "PASSWORD");

        String result = "이메일 전송에 성공하였습니다.";

        SingleResult<String> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(result);

        //mocking
        given(signService.sendEmail(requestDto)).willReturn(result);
        given(responseService.getSingleResult(result)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 후 인증에 대한 이메일을 검증한다.")
    public void verifyMail_register() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("test", "authCode", "VERIFY");

        String result = "이메일 인증이 완료되었습니다.";

        SingleResult<String> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(result);

        //mocking
        given(signService.verify(requestDto)).willReturn(result);
        given(responseService.getSingleResult(result)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 이메일을 검증한다.")
    public void verifyMail_pw() throws Exception {
        //given
        VerifyEmailRequestDto requestDto = new VerifyEmailRequestDto("test", "authCode", "PASSWORD");

        String result = "이메일 인증이 완료되었습니다.";

        SingleResult<String> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(result);

        //mocking
        given(signService.verify(requestDto)).willReturn(result);
        given(responseService.getSingleResult(result)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 변경을 진행한다.")
    public void changePW() throws Exception {
        //given
        MemberChangePwRequestDto requestDto = new MemberChangePwRequestDto("xptmxm1!", "xptmxm1!testChange");

        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "xptmxm1!changedEncodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        MemberChangePwResponseDto responseDto = MemberChangePwResponseDto.create(member);

        SingleResult<MemberChangePwResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(signService.changePassword(requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인을 진행한다.")
    public void login() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("xptmxm1!", "xptmxm1!");

        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        MemberLoginResponseDto responseDto = MemberLoginResponseDto.create(member, List.of(), new JwtTokens("accessToken", "refreshToken"));

        SingleResult<MemberLoginResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(signService.loginMember(requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("RefreshToken을 이용해 JWT 토큰들을 재발급한다.")
    public void reIssue() throws Exception {
        //given
        TokenRequestDto requestDto = new TokenRequestDto("refreshToken");

        Long memberId = 1L;
        Member member = new Member("test", "xptmxm1!encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        TokenResponseDto responseDto = TokenResponseDto.create(member, List.of(), new JwtTokens("newAccessToken", "newRefreshToken"));

        SingleResult<TokenResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(signService.tokenReIssue(requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }

}