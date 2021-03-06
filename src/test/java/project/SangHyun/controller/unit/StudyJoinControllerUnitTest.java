package project.SangHyun.controller.unit;

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
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.factory.study.StudyFactory;
import project.SangHyun.study.studyjoin.controller.StudyJoinController;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinCreateRequestDto;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinUpdateAuthorityRequestDto;
import project.SangHyun.study.studyjoin.controller.dto.response.StudyMembersResponseDto;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinUpdateAuthorityDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;
import project.SangHyun.factory.studyjoin.StudyJoinFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyJoinControllerUnitTest {

    String accessToken;
    Member member;
    Study study;
    StudyJoin studyJoin;

    MockMvc mockMvc;
    @InjectMocks
    StudyJoinController studyJoinController;
    @Mock
    StudyJoinService studyJoinService;
    @Mock
    ResponseService responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyJoinController).build();

        accessToken = "accessToken";
        member = StudyFactory.makeTestAdminMember();
        study = StudyFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyJoin = StudyJoinFactory.makeTestStudyJoinCreator(member, study);
    }

    @Test
    @DisplayName("????????? ????????? ????????????.")
    public void applyJoin() throws Exception {
        //given
        StudyJoinCreateRequestDto createRequestDto = StudyJoinFactory.makeCreateRequestDto("????????? ???????????????.");
        StudyJoinCreateDto createDto = StudyJoinFactory.makeCreateDto("????????? ???????????????.");
        Result ExpectResult = StudyFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyJoinService).applyJoin(study.getId(), member.getId(), createDto);
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/joins/{memberId}", study.getId(), member.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("????????? ????????? ????????????.")
    public void acceptJoin() throws Exception {
        //given
        Result ExpectResult = StudyFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyJoinService).acceptJoin(study.getId(), member.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/joins/{memberId}", study.getId(), member.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("????????? ????????? ????????????.")
    public void rejectJoin() throws Exception {
        //given
        Result ExpectResult = StudyFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyJoinService).rejectJoin(study.getId(), member.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/joins/{memberId}", study.getId(), member.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("???????????? ????????? ?????????????????? ????????? ????????????.")
    public void findStudyMembers() throws Exception {
        //given
        StudyMembersInfoDto studyMember1 = new StudyMembersInfoDto(1L, "?????????1", "profileImgUrl", StudyRole.CREATOR, "????????? ???????????????.");
        StudyMembersInfoDto studyMember2 = new StudyMembersInfoDto(2L, "?????????1", "profileImgUrl", StudyRole.MEMBER, "????????? ???????????????.");
        StudyMembersDto responseDto1 = StudyJoinFactory.makeStudyMembersDto(studyMember1);
        StudyMembersDto responseDto2 = StudyJoinFactory.makeStudyMembersDto(studyMember2);
        List<StudyMembersDto> studyMembersDto = new ArrayList<>(Arrays.asList(responseDto1, responseDto2));
        List<StudyMembersResponseDto> responseDto = studyMembersDto.stream()
                                                        .map(StudyMembersResponseDto::create)
                                                        .collect(Collectors.toList());
        MultipleResult<StudyMembersResponseDto> ExpectResult = StudyJoinFactory.makeMultipleResult(responseDto);

        //mocking
        given(studyJoinService.findStudyMembers(study.getId())).willReturn(studyMembersDto);
        given(responseService.convertToControllerDto(anyList(), any(Function.class))).willReturn(responseDto);
        given(responseService.getMultipleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/members", study.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("????????? ????????? ????????? ????????????.")
    public void changeAuthority() throws Exception {
        //given
        StudyJoinUpdateAuthorityRequestDto updateRequestDto = StudyJoinFactory.makeUpdateAuthorityRequestDto(StudyRole.ADMIN);
        StudyJoinUpdateAuthorityDto requestDto = StudyJoinFactory.makeUpdateAuthorityDto(StudyRole.ADMIN);
        Result ExpectResult = StudyFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyJoinService).updateAuthority(study.getId(), member.getId(), requestDto);
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/authorities/{memberId}", study.getId(), member.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}