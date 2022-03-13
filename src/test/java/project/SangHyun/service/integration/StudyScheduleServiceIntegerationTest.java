package project.SangHyun.study.studyschedule.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.studyschedule.repository.StudyScheduleRepository;
import project.SangHyun.study.studyschedule.service.StudyScheduleService;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleCreateDto;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleUpdateDto;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;
import project.SangHyun.factory.studyschedule.StudyScheduleFactory;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyScheduleServiceIntegerationTest {

    @Autowired
    StudyScheduleService studyScheduleService;
    @Autowired
    StudyScheduleRepository studyScheduleRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("스케줄 생성을 진행한다.")
    public void create() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();
        StudyScheduleCreateDto createDto = StudyScheduleFactory.makeCreateDto();

        //when
        StudyScheduleDto ActualResult = studyScheduleService.createSchedule(study.getId(), createDto);

        //then
        Assertions.assertEquals("백엔드 스터디 일정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스케줄 수정을 진행한다.")
    public void update() throws Exception {
        //given
        StudySchedule studySchedule = testDB.findSchedule();
        StudyScheduleUpdateDto updateDto = StudyScheduleFactory.makeUpdateDto("프론트엔드 공부로 일정 수정");

        //when
        StudyScheduleDto ActualResult = studyScheduleService.updateSchedule(studySchedule.getId(), updateDto);

        //then
        Assertions.assertEquals("프론트엔드 공부로 일정 수정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스케줄을 삭제한다.")
    public void delete() throws Exception {
        //given
        StudySchedule studySchedule = testDB.findSchedule();

        //when, then
        Assertions.assertDoesNotThrow(() -> studyScheduleService.deleteSchedule(studySchedule.getId()));
        Assertions.assertEquals(0,studyScheduleRepository.findAll().size());
    }

    @Test
    @DisplayName("스케줄의 세부사항을 조회한다.")
    public void find() throws Exception {
        //given
        StudySchedule studySchedule = testDB.findSchedule();

        //when
        StudyScheduleDto ActualResult = studyScheduleService.findSchedule(studySchedule.getId());

        //then
        Assertions.assertEquals("JPA 스터디", ActualResult.getTitle());
    }

    @Test
    @DisplayName("모든 스케줄을 조회한다.")
    public void findAll() throws Exception {
        //given
        Study study = testDB.findBackEndStudy();

        //when
        List<StudyScheduleDto> ActualResult = studyScheduleService.findAllSchedules(study.getId());

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }
}