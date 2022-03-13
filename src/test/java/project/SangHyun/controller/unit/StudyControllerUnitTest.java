package project.SangHyun.controller.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.common.dto.response.SliceResponseDto;
import project.SangHyun.helper.AwsS3BucketHelper;
import project.SangHyun.common.dto.response.Result;
import project.SangHyun.common.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.controller.StudyController;
import project.SangHyun.study.study.controller.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.controller.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.controller.dto.response.StudyResponseDto;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.service.dto.response.StudyDto;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.factory.study.StudyFactory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
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
    ResponseService responseService;
    @Mock
    AwsS3BucketHelper awsS3BucketHelper;

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
        Slice slice = new SliceImpl(List.of(study), Pageable.ofSize(6), false);
        SliceResponseDto responseDto = StudyFactory.makeFindAllResponseDto(slice);
        SingleResult<SliceResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.findAllStudiesByDepartment(Long.MAX_VALUE, StudyCategory.CSE, 6)).willReturn(responseDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study")
                        .param("studyId", String.valueOf(Long.MAX_VALUE))
                        .param("department", String.valueOf(StudyCategory.CSE))
                        .param("size", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.numberOfElements").value(1))
                .andExpect(jsonPath("$.data.hasNext").value(false));
    }

    @Test
    @DisplayName("스터디에 대한 세부정보를 로드한다.")
    public void loadStudyDetail() throws Exception {
        //given
        StudyDto studyDto = StudyFactory.makeDto(study);
        StudyResponseDto responseDto = StudyFactory.makeResponseDto(studyDto);
        SingleResult<StudyResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.findStudy(study.getId())).willReturn(studyDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/study/{id}", study.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ExpectResult.getData().getId()));
    }

    @Test
    @DisplayName("스터디를 생성한다.")
    public void createStudy() throws Exception {
        //given
        StudyCreateRequestDto requestDto = StudyFactory.makeCreateRequestDto(member);
        Study study = StudyFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        StudyDto studyDto = StudyFactory.makeDto(study);
        StudyResponseDto responseDto = StudyFactory.makeResponseDto(studyDto);
        SingleResult<StudyResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.createStudy(any())).willReturn(studyDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(multipart("/study")
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("memberId", String.valueOf(requestDto.getMemberId()))
                        .param("title", requestDto.getTitle())
                        .param("startDate", requestDto.getStartDate())
                        .param("endDate", requestDto.getEndDate())
                        .param("description", requestDto.getDescription())
                        .param("tags", requestDto.getTags().toArray(new String[requestDto.getTags().size()]))
                        .param("department", String.valueOf(requestDto.getDepartment()))
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ExpectResult.getData().getId()));
    }

    @Test
    @DisplayName("스터디에 대한 정보를 수정한다.")
    public void updateStudy() throws Exception {
        //given
        StudyUpdateRequestDto requestDto = StudyFactory.makeUpdateRequestDto("테스트 글 수정", List.of("테스트 내용 수정"));
        Study study = StudyFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        StudyDto studyDto = StudyFactory.makeDto(study);
        StudyResponseDto responseDto = StudyFactory.makeResponseDto(studyDto);
        SingleResult<StudyResponseDto> ExpectResult = StudyFactory.makeSingleResult(responseDto);

        //mocking
        given(studyService.updateStudy(any(), any())).willReturn(studyDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(multipart("/study/{studyId}", this.study.getId())
                        .file("profileImg", requestDto.getProfileImg().getBytes())
                        .param("memberId", String.valueOf(member.getId()))
                        .param("title", requestDto.getTitle())
                        .param("startDate", requestDto.getStartDate())
                        .param("endDate", requestDto.getEndDate())
                        .param("description", requestDto.getDescription())
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
                .andExpect(jsonPath("$.data.description").value(ExpectResult.getData().getDescription()));
    }

    @Test
    @DisplayName("스터디를 삭제한다.")
    public void deleteStudy() throws Exception {
        //given
        Result ExpectResult = StudyFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyService).deleteStudy(study.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/study/{id}", study.getId()))
                .andExpect(status().isOk());
    }
}