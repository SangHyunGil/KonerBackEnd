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
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.factory.studyarticle.StudyArticleFactory;
import project.SangHyun.study.studyschedule.controller.StudyScheduleController;
import project.SangHyun.study.studyschedule.controller.dto.request.StudyScheduleCreateRequestDto;
import project.SangHyun.study.studyschedule.controller.dto.request.StudyScheduleUpdateRequestDto;
import project.SangHyun.study.studyschedule.controller.dto.response.StudyScheduleResponseDto;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.studyschedule.service.StudyScheduleService;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleCreateDto;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleUpdateDto;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;
import project.SangHyun.factory.studyschedule.StudyScheduleFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StudyScheduleControllerUnitTest {

    String accessToken;
    Member member;
    Study study;
    StudySchedule studySchedule;

    MockMvc mockMvc;
    @InjectMocks
    StudyScheduleController studyScheduleController;
    @Mock
    StudyScheduleService studyScheduleService;
    @Mock
    ResponseService responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(studyScheduleController).build();

        accessToken = "accessToken";
        member = StudyScheduleFactory.makeTestAdminMember();
        study = StudyScheduleFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studySchedule = StudyScheduleFactory.makeTestSchedule(1L, "????????? ????????? ??????");
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ????????????.")
    public void findSchedules() throws Exception {
        //given
        List<StudyScheduleDto> studyScheduleDto = List.of(studySchedule).stream()
                .map(StudyScheduleFactory::makeDto)
                .collect(Collectors.toList());
        List<StudyScheduleResponseDto> responseDto = studyScheduleDto.stream()
                .map(StudyScheduleFactory::makeResponseDto)
                .collect(Collectors.toList());
        MultipleResult<StudyScheduleResponseDto> ExpectResult = StudyArticleFactory.makeMultipleResult(responseDto);

        //mocking
        given(studyScheduleService.findAllSchedules(study.getId())).willReturn(studyScheduleDto);
        given(responseService.convertToControllerDto(anyList(), any(Function.class))).willReturn(responseDto);
        given(responseService.getMultipleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/schedules/", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("????????? ????????? ??????????????? ????????????.")
    public void findSchedule() throws Exception {
        //given
        StudyScheduleDto studyScheduleDto = StudyScheduleFactory.makeDto(studySchedule);
        StudyScheduleResponseDto responseDto = StudyScheduleFactory.makeResponseDto(studyScheduleDto);
        SingleResult<StudyScheduleResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyScheduleService.findSchedule(studySchedule.getId())).willReturn(studyScheduleDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/schedules/{scheduleId}", study.getId(), studySchedule.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("????????? ???????????? ????????????.")
    public void createComment() throws Exception {
        //given
        StudyScheduleCreateRequestDto createRequestDto = StudyScheduleFactory.makeCreateRequestDto();
        StudyScheduleCreateDto createDto = StudyScheduleFactory.makeCreateDto();
        StudyScheduleDto studyScheduleDto = StudyScheduleFactory.makeDto(studySchedule);
        StudyScheduleResponseDto responseDto = StudyScheduleFactory.makeResponseDto(studyScheduleDto);
        SingleResult<StudyScheduleResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyScheduleService.createSchedule(study.getId(), createDto)).willReturn(studyScheduleDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/schedules", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("????????? ???????????? ????????????.")
    public void updateComment() throws Exception {
        //given
        StudyScheduleUpdateRequestDto updateRequestDto = StudyScheduleFactory.makeUpdateRequestDto("?????? ??????");
        StudyScheduleUpdateDto updateDto = StudyScheduleFactory.makeUpdateDto("?????? ??????");
        StudySchedule updatedSchedule = StudyScheduleFactory.makeTestSchedule(1L, "?????? ??????");
        StudyScheduleDto studyScheduleDto = StudyScheduleFactory.makeDto(updatedSchedule);
        StudyScheduleResponseDto responseDto = StudyScheduleFactory.makeResponseDto(studyScheduleDto);
        SingleResult<StudyScheduleResponseDto> ExpectResult = StudyArticleFactory.makeSingleResult(responseDto);

        //mocking
        given(studyScheduleService.updateSchedule(study.getId(), updateDto)).willReturn(studyScheduleDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/schedules/{scheduleId}", study.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(ExpectResult.getData().getTitle()));
    }

    @Test
    @DisplayName("????????? ???????????? ????????????.")
    public void deleteComment() throws Exception {
        //given
        Result ExpectResult = StudyArticleFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(studyScheduleService).deleteSchedule(studySchedule.getId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/schedules/{scheduleId}", study.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}
