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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.member.controller.MemberController;
import project.SangHyun.member.controller.dto.request.ChangePwRequestDto;
import project.SangHyun.member.controller.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.controller.dto.response.MemberResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.member.service.dto.MemberDto;
import project.SangHyun.member.tools.member.MemberFactory;
import project.SangHyun.member.tools.sign.SignFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.securityContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerUnitTest {
    String accessToken;
    Member authMember;
    Member notAuthMember;

    MockMvc mockMvc;
    @InjectMocks
    MemberController memberController;
    @Mock
    MemberService memberService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();

        accessToken = "accessToken";
        authMember = SignFactory.makeTestAuthMember();
        notAuthMember = SignFactory.makeTestNotAuthMember();
    }

    @Test
    @DisplayName("AccessToken을 이용해 회원의 정보를 로드한다.")
    public void getMemberInfo() throws Exception {
        //given
        MemberDto memberDto = MemberDto.create(authMember);
        MemberResponseDto responseDto = MemberFactory.makeInfoResponseDto(authMember);
        SingleResult<MemberResponseDto> ExpectResult = MemberFactory.makeSingleResult(responseDto);

        //mocking
        given(memberService.getMemberInfo(any())).willReturn(memberDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/users/info")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(2L));
    }


    @Test
    @DisplayName("회원의 유저 프로필을 로드한다.")
    public void getUserProfile() throws Exception {
        //given
        MemberDto memberDto = MemberDto.create(authMember);
        MemberResponseDto responseDto = MemberFactory.makeProfileResponseDto(authMember);
        SingleResult<MemberResponseDto> ExpectResult = MemberFactory.makeSingleResult(responseDto);

        //mocking
        given(memberService.getProfile(any())).willReturn(memberDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/users/{id}", 1)
                        .header("X-AUTH-TOKEN", accessToken)
                        .with(securityContext(SecurityContextHolder.getContext())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(2L));
    }

    @Test
    @DisplayName("회원의 유저 프로필을 수정한다.")
    public void updateMember() throws Exception {
        //given
        MemberDto memberDto = MemberDto.create(authMember);
        MemberUpdateRequestDto requestDto = MemberFactory.makeUpdateRequestDto("상현", "상현이 수정입니다.");
        MemberResponseDto responseDto = MemberFactory.makeUpdateResponseDto(authMember);
        SingleResult<MemberResponseDto> ExpectResult = MemberFactory.makeSingleResult(responseDto);

        //mocking
        given(memberService.updateMember(any(), any())).willReturn(memberDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(multipart("/users/{id}", 1)
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("nickname", requestDto.getNickname())
                        .param("department", String.valueOf(requestDto.getDepartment()))
                        .param("introduction", requestDto.getIntroduction())
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("PUT");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").value("유나"))
                .andExpect(jsonPath("$.data.introduction").value("유나입니다."));
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        Result ExpectResult = MemberFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(memberService).deleteMember(any());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/users/{id}", 1)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 변경을 진행한다.")
    public void changePW() throws Exception {
        //given
        ChangePwRequestDto requestDto = SignFactory.makeChangePwRequestDto(authMember.getEmail(), "change1!");
        Result ExpectResult = SignFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(memberService).changePassword(requestDto);
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)))
                .andExpect(status().isOk());
    }
}