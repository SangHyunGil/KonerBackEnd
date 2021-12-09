package project.SangHyun.web.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.RedisService;
import project.SangHyun.domain.service.SignService;
import project.SangHyun.dto.request.*;
import project.SangHyun.dto.response.MemberChangePwResponseDto;
import project.SangHyun.dto.response.MemberLoginResponseDto;
import project.SangHyun.dto.response.MemberRegisterResponseDto;
import project.SangHyun.dto.response.TokenResponseDto;
import project.SangHyun.web.controller.SignController;

import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SignControllerUnitTest {
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
    public void 회원가입() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = new MemberRegisterRequestDto("xptmxm1!", "xptmxm1!", "테스터", "컴퓨터공학부");

        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "xptmxm1!encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_NOT_PERMITTED);
        ReflectionTestUtils.setField(member, "id", memberId);
        MemberRegisterResponseDto responseDto = MemberRegisterResponseDto.createDto(member);

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExpectResult.getCode()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value(ExpectResult.getMsg()))
                .andExpect(jsonPath("$.data.id").value(ExpectResult.getData().getId()))
                .andExpect(jsonPath("$.data.email").value(ExpectResult.getData().getEmail()));
    }

    @Test
    public void 검증메일발송_처음로그인() throws Exception {
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExpectResult.getCode()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value(ExpectResult.getMsg()))
                .andExpect(jsonPath("$.data").value(ExpectResult.getData()));
    }

    @Test
    public void 검증메일발송_비밀번호변경() throws Exception {
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExpectResult.getCode()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value(ExpectResult.getMsg()))
                .andExpect(jsonPath("$.data").value(ExpectResult.getData()));
    }

    @Test
    public void 이메일검증_처음로그인() throws Exception {
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExpectResult.getCode()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value(ExpectResult.getMsg()))
                .andExpect(jsonPath("$.data").value(ExpectResult.getData()));
    }

    @Test
    public void 이메일검증_비밀번호변경() throws Exception {
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExpectResult.getCode()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value(ExpectResult.getMsg()))
                .andExpect(jsonPath("$.data").value(ExpectResult.getData()));
    }

    @Test
    public void 비밀번호변경() throws Exception {
        //given
        MemberChangePwRequestDto requestDto = new MemberChangePwRequestDto("xptmxm1!", "xptmxm1!testChange");

        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "xptmxm1!changedEncodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        MemberChangePwResponseDto responseDto = MemberChangePwResponseDto.createDto(member);

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExpectResult.getCode()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value(ExpectResult.getMsg()))
                .andExpect(jsonPath("$.data.password").value(ExpectResult.getData().getPassword()));
    }

    @Test
    public void 로그인() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto("xptmxm1!", "xptmxm1!");

        Long memberId = 1L;
        Member member = new Member("xptmxm1!", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        MemberLoginResponseDto responseDto = MemberLoginResponseDto.createDto(member, List.of(), "accessToken", "refreshToken");

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExpectResult.getCode()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value(ExpectResult.getMsg()))
                .andExpect(jsonPath("$.data.id").value(ExpectResult.getData().getId()))
                .andExpect(jsonPath("$.data.email").value(ExpectResult.getData().getEmail()))
                .andExpect(jsonPath("$.data.nickname").value(ExpectResult.getData().getNickname()))
                .andExpect(jsonPath("$.data.department").value(ExpectResult.getData().getDepartment()))
                .andExpect(jsonPath("$.data.accessToken").value(ExpectResult.getData().getAccessToken()))
                .andExpect(jsonPath("$.data.refreshToken").value(ExpectResult.getData().getRefreshToken()));
    }

    @Test
    public void 토큰재발행() throws Exception {
        //given
        ReIssueRequestDto requestDto = new ReIssueRequestDto("refreshToken");

        Long memberId = 1L;
        Member member = new Member("test", "xptmxm1!encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        TokenResponseDto responseDto = TokenResponseDto.createDto(member, List.of(), "newAccessToken", "newRefreshToken");

        SingleResult<TokenResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(signService.reIssue(requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/sign/reissue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ExpectResult.getCode()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value(ExpectResult.getMsg()))
                .andExpect(jsonPath("$.data.id").value(ExpectResult.getData().getId()))
                .andExpect(jsonPath("$.data.email").value(ExpectResult.getData().getEmail()))
                .andExpect(jsonPath("$.data.nickname").value(ExpectResult.getData().getNickname()))
                .andExpect(jsonPath("$.data.department").value(ExpectResult.getData().getDepartment()))
                .andExpect(jsonPath("$.data.accessToken").value(ExpectResult.getData().getAccessToken()))
                .andExpect(jsonPath("$.data.refreshToken").value(ExpectResult.getData().getRefreshToken()));
    }

}