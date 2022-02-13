package project.SangHyun.study.studyjoin.controller.unit;

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
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.study.tools.StudyFactory;
import project.SangHyun.study.studyjoin.controller.StudyJoinController;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinCreateRequestDto;
import project.SangHyun.study.studyjoin.controller.dto.response.StudyMembersResponseDto;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;
import project.SangHyun.study.studyjoin.tools.StudyJoinFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @DisplayName("스터디 참가를 신청한다.")
    public void applyJoin() throws Exception {
        //given
        StudyJoinCreateRequestDto createRequestDto = StudyJoinFactory.makeCreateRequestDto("빠르게 지원합니다.");
        StudyJoinCreateDto createDto = StudyJoinFactory.makeCreateDto("빠르게 지원합니다.");
        Result ExpectResult = StudyFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyJoinService).applyJoin(study.getId(), member.getId(), createDto);
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/"+ study.getId()+"/join/"+member.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 참가를 수락한다.")
    public void acceptJoin() throws Exception {
        //given
        Result ExpectResult = StudyFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyJoinService).acceptJoin(study.getId(), member.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/study/"+ study.getId()+"/join/"+member.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 참가를 거절한다.")
    public void rejectJoin() throws Exception {
        //given
        Result ExpectResult = StudyFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyJoinService).rejectJoin(study.getId(), member.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/study/"+ study.getId()+"/join/"+member.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디에 참여한 스터디원들의 정보를 로드한다.")
    public void findStudyMembers() throws Exception {
        //given
        StudyMembersInfoDto studyMember1 = new StudyMembersInfoDto("테스터1", "profileImgUrl", StudyRole.CREATOR, "빠르게 지원합니다.");
        StudyMembersInfoDto studyMember2 = new StudyMembersInfoDto("테스터1", "profileImgUrl", StudyRole.MEMBER, "빠르게 지원합니다.");
        StudyMembersDto responseDto1 = StudyJoinFactory.makeStudyMembersDto(studyMember1);
        StudyMembersDto responseDto2 = StudyJoinFactory.makeStudyMembersDto(studyMember2);
        List<StudyMembersDto> studyMembersDto = new ArrayList<>(Arrays.asList(responseDto1, responseDto2));
        List<StudyMembersResponseDto> responseDto = studyMembersDto.stream()
                                                        .map(StudyMembersResponseDto::create)
                                                        .collect(Collectors.toList());
        MultipleResult<StudyMembersResponseDto> ExpectResult = StudyJoinFactory.makeMultipleResult(responseDto);

        //mocking
        given(studyJoinService.findStudyMembers(study.getId())).willReturn(studyMembersDto);
        given(responseService.getMultipleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/member", study.getId()))
                .andExpect(status().isOk());
    }
}