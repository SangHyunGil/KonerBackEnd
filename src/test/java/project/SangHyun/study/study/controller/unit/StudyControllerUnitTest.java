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
import project.SangHyun.utils.helper.FileStoreHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    ResponseServiceImpl responseService;
    @Mock
    FileStoreHelper fileStoreHelper;

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
        Study createdStudy = requestDto.toEntity(fileStoreHelper.storeFile(requestDto.getProfileImg()));
        StudyCreateResponseDto responseDto = StudyCreateResponseDto.create(createdStudy);
        SingleResult<StudyCreateResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.createStudy(any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(multipart("/study")
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("memberId", String.valueOf(requestDto.getMemberId()))
                        .param("title", requestDto.getTitle())
                        .param("schedule", requestDto.getSchedule())
                        .param("content", requestDto.getContent())
                        .param("topic", requestDto.getTopic())
                        .param("headCount", String.valueOf(requestDto.getHeadCount()))
                        .param("studyState", String.valueOf(requestDto.getStudyState()))
                        .param("recruitState", String.valueOf(requestDto.getRecruitState()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-AUTH-TOKEN", accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()));
    }
}