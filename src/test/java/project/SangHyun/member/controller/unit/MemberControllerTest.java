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
class MemberControllerTest {
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
    }

    @Test
    @DisplayName("AccessToken을 이용해 회원의 정보를 로드한다.")
    public void getMemberInfo() throws Exception {
        //given
        String accessToken = "accessToken";
        Member member = MemberFactory.makeTestAuthMember();
        MemberInfoResponseDto responseDto = MemberFactory.makeInfoResponseDto(member);
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
        String accessToken = "accessToken";
        Member member = MemberFactory.makeTestAuthMember();
        MemberProfileResponseDto responseDto = MemberFactory.makeProfileResponseDto(member);
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
        String accessToken = "accessToken";
        MemberUpdateRequestDto requestDto = MemberFactory.makeUpdateRequestDto("상현");
        Member member = MemberFactory.makeTestAuthMember();
        MemberUpdateResponseDto responseDto = MemberFactory.makeUpdateResponseDto(member);
        SingleResult<MemberUpdateResponseDto> ExpectResult = MemberFactory.makeSingleResult(responseDto);

        //mocking
        given(memberService.updateMember(any(), any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").value("상현"));
    }

    @Test
    @DisplayName("회원을 삭제한다.")
    public void deleteMember() throws Exception {
        //given
        String accessToken = "accessToken";
        Member member = MemberFactory.makeTestAuthMember();
        MemberDeleteResponseDto responseDto = MemberFactory.makeDeleteResponseDto(member);
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