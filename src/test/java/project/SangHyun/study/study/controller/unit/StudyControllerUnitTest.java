package project.SangHyun.study.study.controller.unit;

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
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.study.study.tools.StudyFactory;
import project.SangHyun.study.studyboard.service.StudyBoardService;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.study.controller.StudyController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyControllerUnitTest {
    String accessToken;
    Member member;
    Study study;

    MockMvc mockMvc;
    @InjectMocks
    StudyController studyController;
    @Mock
    StudyService studyService;
    @Mock
    StudyJoinService studyJoinService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyController).build();

        accessToken = "accessToken";
        member = StudyFactory.makeTestAuthMember();
        study = StudyFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    @DisplayName("스터디 정보를 로드한다.")
    public void loadStudyInfo() throws Exception {
        //given
        List<StudyFindResponseDto> responseDtos = StudyFactory.makeFindAllResponseDto(study);
        MultipleResult<StudyFindResponseDto> ExpectResult = StudyFactory.makeMultipleResult(responseDtos);

        //mocking
        given(studyService.findAllStudies()).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1));
    }

    @Test
    @DisplayName("스터디에 대한 세부정보를 로드한다.")
    public void loadStudyDetail() throws Exception {
        //given
        StudyFindResponseDto responseDto = StudyFactory.makeFindResponseDto(study);
        SingleResult<StudyFindResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.findStudy(1L)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()));
    }

    @Test
    @DisplayName("스터디를 생성한다.")
    public void createStudy() throws Exception {
        //given
        StudyCreateRequestDto requestDto = StudyFactory.makeCreateDto(member);
        Study createdStudy = requestDto.toEntity();
        StudyCreateResponseDto responseDto = StudyCreateResponseDto.create(createdStudy);
        SingleResult<StudyCreateResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.createStudy(requestDto)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/study")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()));
    }

    @Test
    @DisplayName("스터디에 참여한다.")
    public void join() throws Exception {
        //given
        StudyJoinRequestDto requestDto = new StudyJoinRequestDto(study.getId(), member.getId());
        StudyJoin studyJoin = StudyFactory.makeTestStudyJoin(member, study);
        StudyJoinResponseDto responseDto = StudyJoinResponseDto.create(studyJoin);
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
        Study study = StudyFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        StudyJoinRequestDto requestDto = new StudyJoinRequestDto(study.getId(), member.getId());

        Long studyId = 1L;
        StudyMembersInfoDto studyMember1 = new StudyMembersInfoDto(1L, "테스터1", StudyRole.CREATOR);
        StudyMembersInfoDto studyMember2 = new StudyMembersInfoDto(1L, "테스터1", StudyRole.MEMBER);
        StudyFindMembersResponseDto responseDto1 = StudyFindMembersResponseDto.create(studyMember1);
        StudyFindMembersResponseDto responseDto2 = StudyFindMembersResponseDto.create(studyMember2);
        List<StudyFindMembersResponseDto> responseDtos = new ArrayList<>(Arrays.asList(responseDto1, responseDto2));

        MultipleResult<StudyFindMembersResponseDto> ExpectResult = new MultipleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDtos);

        //mocking
        given(studyJoinService.findStudyMembers(studyId)).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{studyId}/member", studyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}