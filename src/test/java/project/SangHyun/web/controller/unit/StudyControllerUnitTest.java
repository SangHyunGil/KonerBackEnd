package project.SangHyun.web.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.*;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.dto.request.study.StudyCreateRequestDto;
import project.SangHyun.dto.request.study.StudyJoinRequestDto;
import project.SangHyun.dto.request.study.StudyUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyCreateResponseDto;
import project.SangHyun.dto.response.study.StudyFindResponseDto;
import project.SangHyun.dto.response.study.StudyJoinResponseDto;
import project.SangHyun.dto.response.study.StudyUpdateResponseDto;
import project.SangHyun.web.controller.StudyController;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudyControllerUnitTest {
    MockMvc mockMvc;
    @InjectMocks
    StudyController studyController;
    @Mock
    StudyService studyService;
    @Mock
    StudyBoardService studyBoardService;
    @Mock
    StudyJoinService studyJoinService;
    @Mock
    ResponseServiceImpl responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyController).build();
    }

    @Test
    public void 스터디정보로드() throws Exception {
        //given
        Long studyId = 1L;
        Study study = new Study("백엔드 모집", "백엔드", "백엔드 모집합니다.", StudyState.STUDYING, RecruitState.PROCEED, 3L, new Member(1L), new ArrayList<>(), new ArrayList<>());
        ReflectionTestUtils.setField(study, "id", studyId);
        List<StudyFindResponseDto> responseDtos = List.of(StudyFindResponseDto.createDto(study));

        MultipleResult<StudyFindResponseDto> ExpectResult = new MultipleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDtos);

        //mocking
        given(studyService.findAllStudies()).willReturn(responseDtos);
        given(responseService.getMultipleResult(responseDtos)).willReturn(ExpectResult);


        //when, then
        mockMvc.perform(get("/study"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].studyId").value(ExpectResult.getData().get(0).getStudyId()))
                .andExpect(jsonPath("$.data[0].memberId").value(ExpectResult.getData().get(0).getMemberId()))
                .andExpect(jsonPath("$.data[0].title").value(ExpectResult.getData().get(0).getTitle()))
                .andExpect(jsonPath("$.data[0].topic").value(ExpectResult.getData().get(0).getTopic()))
                .andExpect(jsonPath("$.data[0].content").value(ExpectResult.getData().get(0).getContent()));
    }

    @Test
    public void 스터디세부정보로드() throws Exception {
        //given
        Long studyId = 1L;
        Study study = new Study("백엔드 모집", "백엔드", "백엔드 모집합니다.", StudyState.STUDYING, RecruitState.PROCEED, 3L, new Member(1L), new ArrayList<>(), new ArrayList<>());
        ReflectionTestUtils.setField(study, "id", studyId);
        StudyFindResponseDto responseDto = StudyFindResponseDto.createDto(study);

        SingleResult<StudyFindResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(studyService.findStudy(1L)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()))
                .andExpect(jsonPath("$.data.memberId").value(ExpectResult.getData().getMemberId()))
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()))
                .andExpect(jsonPath("$.data.topic").value(ExpectResult.getData().getTopic()))
                .andExpect(jsonPath("$.data.content").value(ExpectResult.getData().getContent()))
        ;
    }

    @Test
    public void 스터디생성() throws Exception {
        //given
        String accessToken = "accessToken";
        StudyCreateRequestDto requestDto = new StudyCreateRequestDto(1L, "백엔드 모집", "백엔드", "백엔드 모집합니다.", 2L, StudyState.STUDYING, RecruitState.PROCEED);

        Long studyId = 1L;
        Study study = new Study("백엔드 모집", "백엔드", "백엔드 모집합니다.", StudyState.STUDYING, RecruitState.PROCEED, 3L, new Member(1L), new ArrayList<>(), new ArrayList<>());
        ReflectionTestUtils.setField(study, "id", studyId);
        StudyCreateResponseDto responseDto = StudyCreateResponseDto.createDto(study);

        SingleResult<StudyCreateResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

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
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()))
                .andExpect(jsonPath("$.data.memberId").value(ExpectResult.getData().getMemberId()))
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()))
                .andExpect(jsonPath("$.data.topic").value(ExpectResult.getData().getTopic()))
                .andExpect(jsonPath("$.data.content").value(ExpectResult.getData().getContent()));
    }

    @Test
    public void 스터디참여() throws Exception {
        //given
        String accessToken = "accessToken";
        StudyJoinRequestDto requestDto = new StudyJoinRequestDto(1L, 1L);

        Long studyJoinId = 1L;
        StudyJoin studyJoin = new StudyJoin(new Member(1L), new Study(1L), StudyRole.CREATOR);
        ReflectionTestUtils.setField(studyJoin, "id", studyJoinId);
        StudyJoinResponseDto responseDto = StudyJoinResponseDto.createDto(studyJoin);

        SingleResult<StudyJoinResponseDto> ExpectResult = new SingleResult<>();
        ExpectResult.setCode(0); ExpectResult.setSuccess(true); ExpectResult.setMsg("성공");
        ExpectResult.setData(responseDto);

        //mocking
        given(studyJoinService.join(requestDto)).willReturn(responseDto);
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
}