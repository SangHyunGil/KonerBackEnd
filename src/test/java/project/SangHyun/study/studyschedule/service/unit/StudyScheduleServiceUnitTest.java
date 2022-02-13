package project.SangHyun.study.studyschedule.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.studyschedule.repository.StudyScheduleRepository;
import project.SangHyun.study.studyschedule.service.StudyScheduleService;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleCreateDto;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleUpdateDto;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;
import project.SangHyun.study.studyschedule.tools.StudyScheduleFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class StudyScheduleServiceUnitTest {

    Member member;
    Study study;
    StudySchedule studySchedule;

    @InjectMocks
    StudyScheduleService studyScheduleService;
    @Mock
    StudyScheduleRepository studyScheduleRepository;

    @BeforeEach
    public void init() {
        member = StudyScheduleFactory.makeTestAuthMember();
        study = StudyScheduleFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studySchedule = StudyScheduleFactory.makeTestSchedule(1L);
    }

    @Test
    @DisplayName("스케줄 생성을 진행한다.")
    public void create() throws Exception {
        //given
        StudyScheduleCreateDto createDto = StudyScheduleFactory.makeCreateDto();
        StudyScheduleDto ExpectResult = StudyScheduleFactory.makeDto(studySchedule);

        //mocking
        given(studyScheduleRepository.save(any())).willReturn(studySchedule);

        //when
        StudyScheduleDto ActualResult = studyScheduleService.createSchedule(study.getId(), createDto);

        //then
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
    }

    @Test
    @DisplayName("스케줄 수정을 진행한다.")
    public void update() throws Exception {
        //given
        StudyScheduleUpdateDto updateDto = StudyScheduleFactory.makeUpdateDto("프론트엔드 공부로 일정 수정");

        //mocking
        given(studyScheduleRepository.findById(any())).willReturn(Optional.ofNullable(studySchedule));

        //when
        StudyScheduleDto ActualResult = studyScheduleService.updateSchedule(studySchedule.getId(), updateDto);

        //then
        Assertions.assertEquals("프론트엔드 공부로 일정 수정", ActualResult.getTitle());
    }

    @Test
    @DisplayName("스케줄을 삭제한다.")
    public void delete() throws Exception {
        //given

        //mocking
        willDoNothing().given(studyScheduleRepository).deleteById(studySchedule.getId());

        //when, then
        Assertions.assertDoesNotThrow(() -> studyScheduleService.deleteSchedule(studySchedule.getId()));
    }

    @Test
    @DisplayName("스케줄의 세부사항을 조회한다.")
    public void find() throws Exception {
        //given

        //mocking
        given(studyScheduleRepository.findById(any())).willReturn(Optional.ofNullable(studySchedule));

        //when
        StudyScheduleDto ActualResult = studyScheduleService.findSchedule(studySchedule.getId());

        //then
        Assertions.assertEquals(studySchedule.getTitle(), ActualResult.getTitle());
    }

    @Test
    @DisplayName("모든 스케줄을 조회한다.")
    public void findAll() throws Exception {
        //given

        //mocking
        given(studyScheduleRepository.findAllByStudyId(study.getId())).willReturn(List.of(studySchedule));

        //when
        List<StudyScheduleDto> ActualResult = studyScheduleService.findAllSchedules(study.getId());

        //then
        Assertions.assertEquals(1, ActualResult.size());
    }

}