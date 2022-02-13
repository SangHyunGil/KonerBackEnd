package project.SangHyun.study.studyschedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.study.studyschedule.repository.StudyScheduleRepository;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleCreateDto;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleUpdateDto;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyScheduleService {

    private StudyScheduleRepository studyScheduleRepository;

    public StudyScheduleDto createSchedule(Long studyId, StudyScheduleCreateDto requestDto) {
        return null;
    }

    public StudyScheduleDto updateSchedule(Long scheduleId, StudyScheduleUpdateDto requestDto) {
        return null;
    }

    public void deleteSchedule(Long scheduleId) {
    }

    public StudyScheduleDto findSchedule(Long scheduleId) {
        return null;
    }

    public List<StudyScheduleDto> findAllSchedules(Long studyId) {
        return null;
    }
}
