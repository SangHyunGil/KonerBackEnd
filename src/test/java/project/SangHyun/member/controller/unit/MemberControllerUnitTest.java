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
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.tools.member.MemberFactory;
import project.SangHyun.member.tools.sign.SignFactory;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;
import project.SangHyun.member.controller.MemberController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        MemberInfoResponseDto responseDto = MemberFactory.makeInfoResponseDto(authMember);
        SingleResult<MemberInfoResponseDto> ExpectResult = MemberFactory.makeSingleResult(responseDto);

        //mocking
        given(memberService.getMemberInfo(any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/users/info")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(1L));
    }


    @Test
    @DisplayName("회원의 유저 프로필을 로드한다.")
    public void getUserProfile() throws Exception {
        //given
        MemberProfileResponseDto responseDto = MemberFactory.makeProfileResponseDto(authMember);
        SingleResult<MemberProfileResponseDto> ExpectResult = MemberFactory.makeSingleResult(responseDto);

        //mocking
        given(memberService.getProfile(any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/users/{id}", 1)
                        .header("X-AUTH-TOKEN", accessToken)
                        .with(securityContext(SecurityContextHolder.getContext())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(1L));
    }

    @Test
    @DisplayName("회원의 유저 프로필을 수정한다.")
    public void updateMember() throws Exception {
        //given
        MemberUpdateRequestDto requestDto = MemberFactory.makeUpdateRequestDto("상현");
        MemberUpdateResponseDto responseDto = MemberFactory.makeUpdateResponseDto(authMember);
        SingleResult<MemberUpdateResponseDto> ExpectResult = MemberFactory.makeSingleResult(responseDto);

        //mocking
        given(memberService.updateMember(any(), any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(multipart("/users/{id}", 1)
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("email", requestDto.getEmail())
                        .param("nickname", requestDto.getNickname())
                        .param("department", requestDto.getDepartment())
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("PUT");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").value("상현"));
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        MemberDeleteResponseDto responseDto = MemberFactory.makeDeleteResponseDto(authMember);
        SingleResult<MemberDeleteResponseDto> ExpectResult = MemberFactory.makeSingleResult(responseDto);

        //mocking
        given(memberService.deleteMember(any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/users/{id}", 1)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(1L));
    }
}