package project.SangHyun.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import project.SangHyun.factory.studyschedule.StudyScheduleFactory;
import project.SangHyun.study.studyschedule.controller.dto.request.StudyScheduleCreateRequestDto;
import project.SangHyun.study.studyschedule.controller.dto.request.StudyScheduleUpdateRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudyScheduleControllerIntegrationTest extends ControllerIntegrationTest{

    @Test
    @DisplayName("스터디 스케줄을 모두 로드한다.")
    public void findAllSchedules() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/schedules", backendStudy.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디 스케줄을 모두 로드할 수 있다.")
    public void findAllSchedules2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/schedules", backendStudy.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스케줄을 로드할 수 없다.")
    public void findAllSchedules_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/schedules", backendStudy.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 스케줄을 생성한다.")
    public void createSchedule() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyScheduleCreateRequestDto requestDto = StudyScheduleFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/schedules", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스케줄을 생성할 수 없다.")
    public void createSchedule_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());
        StudyScheduleCreateRequestDto requestDto = StudyScheduleFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/schedules", backendStudy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 스케줄을 수정한다.")
    public void updateSchedule() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());
        StudyScheduleUpdateRequestDto requestDto = StudyScheduleFactory.makeUpdateRequestDto("일정 수정");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("일정 수정"));
    }

    @Test
    @DisplayName("웹 관리자는 부적절한 스터디 스케줄을 수정할 수 있다.")
    public void updateSchedule2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());
        StudyScheduleUpdateRequestDto requestDto = StudyScheduleFactory.makeUpdateRequestDto("일정 수정");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("일정 수정"));
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스터디 스케줄을 수정할 수 없다.")
    public void updateSchedule_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());
        StudyScheduleUpdateRequestDto requestDto = StudyScheduleFactory.makeUpdateRequestDto("일정 수정");

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 스케줄을 삭제한다.")
    public void deleteSchedule() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 부적절한 스터디 스케줄을 삭제할 수 있다.")
    public void deleteSchedule2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스터디 스케줄을 삭제할 수 없다.")
    public void deleteSchedule_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 스케줄의 세부사항을 조회한다.")
    public void findSchedule() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(studyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(studySchedule.getTitle().getTitle()));
    }

    @Test
    @DisplayName("웹 관리자는 스터디 스케줄의 세부사항을 조회할 수 있다.")
    public void findSchedule2() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(webAdminMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(studySchedule.getTitle().getTitle()));
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스터디 스케줄의 세부사항을 조회할 수 없다.")
    public void findSchedule_fail() throws Exception {
        //given
        String accessToken = accessTokenHelper.createToken(notStudyMember.getEmail());

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/schedules/{scheduleId}", backendStudy.getId(), studySchedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}
