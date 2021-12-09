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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.MemberService;
import project.SangHyun.domain.service.SignService;
import project.SangHyun.dto.request.MemberUpdateInfoRequestDto;
import project.SangHyun.dto.response.MemberInfoResponseDto;
import project.SangHyun.dto.response.MemberProfileResponseDto;
import project.SangHyun.dto.response.MemberRegisterResponseDto;
import project.SangHyun.dto.response.MemberUpdateInfoResponseDto;
import project.SangHyun.web.controller.SignController;
import project.SangHyun.web.controller.UserController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {
    MockMvc mockMvc;
    @InjectMocks
    UserController userController;
    @Mock
    MemberService memberService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void 유저정보로드_성공() throws Exception {
        //given
        String accessToken = "accessToken";

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        MemberInfoResponseDto responseDto = MemberInfoResponseDto.createDto(member, List.of());

        SingleResult<MemberInfoResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(memberService.getMemberInfo(any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/users/info")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(1L))
                .andExpect(jsonPath("$.data.email").value("test"))
                .andExpect(jsonPath("$.data.nickname").value("테스터"))
                .andExpect(jsonPath("$.data.department").value("컴퓨터공학부"));
    }


    @Test
    public void 유저프로필로드() throws Exception {
        //given
        String accessToken = "accessToken";

        Long memberId = 1L;
        Member member = new Member("test", "encodedPW", "테스터", "컴퓨터공학부", MemberRole.ROLE_MEMBER);
        ReflectionTestUtils.setField(member, "id", memberId);
        MemberProfileResponseDto responseDto = MemberProfileResponseDto.createDto(member);

        SingleResult<MemberProfileResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(memberService.getProfile(any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/users/{id}", 1)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(1L))
                .andExpect(jsonPath("$.data.email").value("test"))
                .andExpect(jsonPath("$.data.nickname").value("테스터"))
                .andExpect(jsonPath("$.data.department").value("컴퓨터공학부"));
    }

//    @Test
//    @WithMockUser(username = "test")
//    public void 유저프로필수정() throws Exception {
//        //given
//        String accessToken = "accessToken";
//        MemberDetails memberDetails = new MemberDetails("test", "encodedPW",
//                Collections.singletonList(new SimpleGrantedAuthority(MemberRole.ROLE_MEMBER.toString())));
//        MemberUpdateInfoRequestDto requestDto = new MemberUpdateInfoRequestDto("test", "테스터 수정", "기계공학부");
//
//        Long memberId = 1L;
//        Member member = new Member("test", "encodedPW", "테스터 수정", "기계공학부", MemberRole.ROLE_MEMBER);
//        ReflectionTestUtils.setField(member, "id", memberId);
//        MemberUpdateInfoResponseDto responseDto = MemberUpdateInfoResponseDto.createDto(member);
//
//        SingleResult<MemberUpdateInfoResponseDto> ExpectResult = new SingleResult<>();
//        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
//        ExpectResult.setData(responseDto);
//
//        //mocking
//        given(memberService.updateMemberInfo(any(), any())).willReturn(responseDto);
//        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);
//
//        //when, then
//        mockMvc.perform(put("/users/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("utf-8")
//                        .content(new Gson().toJson(requestDto))
//                        .header("X-AUTH-TOKEN", accessToken))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.memberId").value(1L))
//                .andExpect(jsonPath("$.data.email").value("test"))
//                .andExpect(jsonPath("$.data.nickname").value("테스터 수정"))
//                .andExpect(jsonPath("$.data.department").value("기계공학부"));
//    }
}