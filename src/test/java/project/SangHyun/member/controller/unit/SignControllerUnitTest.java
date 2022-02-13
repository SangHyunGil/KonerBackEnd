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
import project.SangHyun.member.controller.SignController;
import project.SangHyun.member.controller.dto.request.LoginRequestDto;
import project.SangHyun.member.controller.dto.request.MemberRegisterRequestDto;
import project.SangHyun.member.controller.dto.request.TokenRequestDto;
import project.SangHyun.member.controller.dto.response.LoginResponseDto;
import project.SangHyun.member.controller.dto.response.MemberResponseDto;
import project.SangHyun.member.controller.dto.response.TokenResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.service.SignService;
import project.SangHyun.member.service.dto.response.MemberDto;
import project.SangHyun.member.tools.sign.SignFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SignControllerUnitTest {
    Member authMember;
    Member notAuthMember;

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

        authMember = SignFactory.makeTestAuthMember();
        notAuthMember = SignFactory.makeTestNotAuthMember();
    }

    @Test
    @DisplayName("회원가입을 진행한다.")
    public void register() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = SignFactory.makeRegisterRequestDto();
        Member createdMember = requestDto.toServiceDto().toEntity("encodedPassword", "https://s3.console.aws.amazon.com/s3/object/koner-bucket?region=ap-northeast-2&prefix=profileImg/koryong1.jpg");
        MemberDto memberDto = SignFactory.makeMemberDto(createdMember);
        MemberResponseDto responseDto = SignFactory.makeRegisterResponseDto(memberDto);
        SingleResult<MemberResponseDto> ExpectResult = SignFactory.makeSingleResult(responseDto);

        //mocking
        given(signService.registerMember(any())).willReturn(memberDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

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
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인을 진행한다.")
    public void login() throws Exception {
        //given
        LoginRequestDto requestDto = SignFactory.makeAuthMemberLoginRequestDto();
        LoginResponseDto responseDto = SignFactory.makeLoginResponseDto(authMember);
        SingleResult<LoginResponseDto> ExpectResult = SignFactory.makeSingleResult(responseDto);

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
        TokenRequestDto requestDto = SignFactory.makeTokenRequestDto("refreshToken");
        TokenResponseDto responseDto = SignFactory.makeTokenResponseDto(authMember);
        SingleResult<TokenResponseDto> ExpectResult = SignFactory.makeSingleResult(responseDto);

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