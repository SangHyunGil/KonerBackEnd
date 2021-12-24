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
import project.SangHyun.member.domain.Member;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.study.controller.StudyController;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.study.study.tools.StudyFactory;
import project.SangHyun.study.studyjoin.controller.StudyJoinController;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyjoin.tools.StudyJoinFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyJoinController).build();

        accessToken = "accessToken";
        member = StudyFactory.makeTestAuthMember();
        study = StudyFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyJoin = StudyJoinFactory.makeTestStudyJoin(member, study);
    }

    @Test
    @DisplayName("스터디에 참여한다.")
    public void join() throws Exception {
        //given
        StudyJoinRequestDto requestDto = StudyJoinFactory.makeCreateDto(study, member);
        StudyJoin createdStudyJoin = requestDto.toEntity();
        StudyJoinResponseDto responseDto = StudyJoinResponseDto.create(createdStudyJoin);
        SingleResult<StudyJoinResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyJoinService.joinStudy(requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyJoinId").value(ExpectResult.getData().getStudyJoinId()))
                .andExpect(jsonPath("$.data.memberId").value(ExpectResult.getData().getMemberId()));
    }

    @Test
    @DisplayName("스터디에 참여한 스터디원들의 정보를 로드한다.")
    public void findStudyMembers() throws Exception {
        //given
        StudyMembersInfoDto studyMember1 = new StudyMembersInfoDto(member.getId(), "테스터1", StudyRole.CREATOR);
        StudyMembersInfoDto studyMember2 = new StudyMembersInfoDto(member.getId(), "테스터1", StudyRole.MEMBER);
        StudyFindMembersResponseDto responseDto1 = StudyFindMembersResponseDto.create(studyMember1);
        StudyFindMembersResponseDto responseDto2 = StudyFindMembersResponseDto.create(studyMember2);
        List<StudyFindMembersResponseDto> responseDtos = new ArrayList<>(Arrays.asList(responseDto1, responseDto2));
        MultipleResult<StudyFindMembersResponseDto> ExpectResult = StudyJoinFactory.makeMultipleResult(responseDtos);

        //mocking
        given(studyJoinService.findStudyMembers(study.getId())).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/member", study.getId()))
                .andExpect(status().isOk());
    }
}