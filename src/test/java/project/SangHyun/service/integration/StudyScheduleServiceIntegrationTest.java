package project.SangHyun.study.studyschedule.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.factory.studyschedule.StudyScheduleFactory;
import project.SangHyun.service.integration.ServiceIntegrationTest;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleCreateDto;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleUpdateDto;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;

import java.util.List;

class StudyScheduleServiceIntegrationTest extends ServiceIntegrationTest {

    @Test
    @DisplayName("스케줄 생성을 진행한다.")
    public void create() throws Exception {
        //given
        StudyScheduleCreateDto createDto = StudyScheduleFactory.makeCreateDto();

        //when
        StudyScheduleDto ActualResult = studyScheduleService.createSchedule(backendStudy.getId(), createDto);

        //then
        Assertions.assertEquals("백엔드 스터디 일정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스케줄 수정을 진행한다.")
    public void update() throws Exception {
        //given
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

        //when, then
        Assertions.assertDoesNotThrow(() -> studyScheduleService.deleteSchedule(studySchedule.getId()));
        Assertions.assertEquals(0,studyScheduleRepository.findAll().size());
    }

    @Test
    @DisplayName("스케줄의 세부사항을 조회한다.")
    public void find() throws Exception {
        //given

        //when
        StudyScheduleDto ActualResult = studyScheduleService.findSchedule(studySchedule.getId());

        //then
        Assertions.assertEquals("JPA 스터디", ActualResult.getTitle());
    }

    @Test
    @DisplayName("모든 스케줄을 조회한다.")
    public void findAll() throws Exception {
        //given

        //when
        List<StudyScheduleDto> ActualResult = studyScheduleService.findAllSchedules(backendStudy.getId());

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }
}