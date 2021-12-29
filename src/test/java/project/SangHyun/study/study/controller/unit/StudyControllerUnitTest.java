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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyDeleteResponseDto;
import project.SangHyun.study.study.dto.response.StudyUpdateResponseDto;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.study.study.tools.StudyFactory;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.controller.StudyController;
import project.SangHyun.helper.FileStoreHelper;

import java.util.ArrayList;
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
        given(studyService.findStudy(study.getId())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{id}", study.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()));
    }

    @Test
    @DisplayName("스터디를 생성한다.")
    public void createStudy() throws Exception {
        //given
        StudyCreateRequestDto requestDto = StudyFactory.makeCreateRequestDto(member);
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
                        .param("tags", requestDto.getTags().toArray(new String[requestDto.getTags().size()]))
                        .param("headCount", String.valueOf(requestDto.getHeadCount()))
                        .param("studyMethod", String.valueOf(requestDto.getStudyMethod()))
                        .param("studyState", String.valueOf(requestDto.getStudyState()))
                        .param("recruitState", String.valueOf(requestDto.getRecruitState()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("POST");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()));
    }

    @Test
    @DisplayName("스터디에 대한 정보를 수정한다.")
    public void updateStudy() throws Exception {
        //given
        StudyUpdateRequestDto requestDto = StudyFactory.makeUpdateRequestDto("테스트 글 수정", List.of("테스트 내용 수정"));
        StudyUpdateResponseDto responseDto = StudyFactory.makeUpdateResponseDto(study, "테스트 글 수정", "테스트 내용 수정");
        SingleResult<StudyUpdateResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.updateStudy(any(), any())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(multipart("/study/{studyId}", study.getId())
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("memberId", String.valueOf(member.getId()))
                        .param("title", requestDto.getTitle())
                        .param("schedule", requestDto.getSchedule())
                        .param("content", requestDto.getContent())
                        .param("tags", requestDto.getTags().toArray(new String[requestDto.getTags().size()]))
                        .param("headCount", String.valueOf(requestDto.getHeadCount()))
                        .param("studyMethod", String.valueOf(requestDto.getStudyMethod()))
                        .param("studyState", String.valueOf(requestDto.getStudyState()))
                        .param("recruitState", String.valueOf(requestDto.getRecruitState()))
                        .with(requestPostProcessor -> {
                            requestPostProcessor.setMethod("PUT");
                            return requestPostProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()))
                .andExpect(jsonPath("$.data.content").value(ExpectResult.getData().getContent()));
    }

    @Test
    @DisplayName("스터디를 삭제한다.")
    public void deleteStudy() throws Exception {
        //given
        StudyDeleteResponseDto responseDto = StudyFactory.makeDeleteResponseDto(study);
        SingleResult<StudyDeleteResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.deleteStudy(study.getId())).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/study/{id}", study.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.studyId").value(ExpectResult.getData().getStudyId()));
    }
}