package project.SangHyun.study.studyschedule.controller.integration;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import project.SangHyun.TestDB;
import project.SangHyun.config.jwt.JwtTokenHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyschedule.controller.dto.request.StudyScheduleCreateRequestDto;
import project.SangHyun.study.studyschedule.controller.dto.request.StudyScheduleUpdateRequestDto;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.studyschedule.tools.StudyScheduleFactory;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class StudyScheduleControllerIntegrationTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    JwtTokenHelper accessTokenHelper;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        testDB.init();
    }

    @Test
    @DisplayName("스터디 스케줄을 모두 로드한다.")
    public void findAllSchedules() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();

        //when, then
        mockMvc.perform(get("/study/{studyId}/schedule", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 스터디 스케줄을 모두 로드할 수 있다.")
    public void findAllSchedules2() throws Exception {
        //given
        Member member = testDB.findAdminMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();

        //when, then
        mockMvc.perform(get("/study/{studyId}/schedule", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스케줄을 로드할 수 없다.")
    public void findAllSchedules_fail() throws Exception {
        //given
        Member member = testDB.findNotStudyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();

        //when, then
        mockMvc.perform(get("/study/{studyId}/schedule", study.getId())
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 스케줄을 생성한다.")
    public void createSchedule() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyScheduleCreateRequestDto requestDto = StudyScheduleFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/study/{studyId}/schedule", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(requestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스케줄을 생성할 수 없다.")
    public void createSchedule_fail() throws Exception {
        //given
        Member member = testDB.findNotStudyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudyScheduleCreateRequestDto requestDto = StudyScheduleFactory.makeCreateRequestDto();

        //when, then
        mockMvc.perform(post("/study/{studyId}/schedule", study.getId())
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
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();
        StudyScheduleUpdateRequestDto requestDto = StudyScheduleFactory.makeUpdateRequestDto("일정 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
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
        Member member = testDB.findAdminMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();
        StudyScheduleUpdateRequestDto requestDto = StudyScheduleFactory.makeUpdateRequestDto("일정 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
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
        Member member = testDB.findNotStudyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();
        StudyScheduleUpdateRequestDto requestDto = StudyScheduleFactory.makeUpdateRequestDto("일정 수정");

        //when, then
        mockMvc.perform(put("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
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
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("웹 관리자는 부적절한 스터디 스케줄을 삭제할 수 있다.")
    public void deleteSchedule2() throws Exception {
        //given
        Member member = testDB.findAdminMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스터디 스케줄을 삭제할 수 없다.")
    public void deleteSchedule_fail() throws Exception {
        //given
        Member member = testDB.findNotStudyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();

        //when, then
        mockMvc.perform(delete("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("스터디 스케줄의 세부사항을 조회한다.")
    public void findSchedule() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();

        //when, then
        mockMvc.perform(get("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(schedule.getTitle().getTitle()));
    }

    @Test
    @DisplayName("웹 관리자는 스터디 스케줄의 세부사항을 조회할 수 있다.")
    public void findSchedule2() throws Exception {
        //given
        Member member = testDB.findAdminMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();

        //when, then
        mockMvc.perform(get("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(schedule.getTitle().getTitle()));
    }

    @Test
    @DisplayName("스터디 멤버가 아니면 스터디 스케줄의 세부사항을 조회할 수 없다.")
    public void findSchedule_fail() throws Exception {
        //given
        Member member = testDB.findNotStudyMember();
        String accessToken = accessTokenHelper.createToken(member.getEmail());
        Study study = testDB.findBackEndStudy();
        StudySchedule schedule = testDB.findSchedule();

        //when, then
        mockMvc.perform(get("/study/{studyId}/schedule/{scheduleId}", study.getId(), schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().is3xxRedirection());
    }
}
