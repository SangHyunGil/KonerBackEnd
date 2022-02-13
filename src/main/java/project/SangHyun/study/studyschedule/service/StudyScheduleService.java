package project.SangHyun.study.studyschedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.StudyScheduleNotFoundException;
import project.SangHyun.study.studyschedule.domain.StudySchedule;
import project.SangHyun.study.studyschedule.repository.StudyScheduleRepository;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleCreateDto;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleUpdateDto;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyScheduleService {

    private final StudyScheduleRepository studyScheduleRepository;

    public StudyScheduleDto createSchedule(Long studyId, StudyScheduleCreateDto requestDto) {
        StudySchedule studySchedule = studyScheduleRepository.save(requestDto.toEntity(studyId));
        return StudyScheduleDto.create(studySchedule);
    }

    public StudyScheduleDto updateSchedule(Long scheduleId, StudyScheduleUpdateDto requestDto) {
        StudySchedule studySchedule = findStudyScheduleById(scheduleId);
        studySchedule.update(requestDto.toEntity());
        return StudyScheduleDto.create(studySchedule);
    }

    public void deleteSchedule(Long scheduleId) {
        StudySchedule studySchedule = findStudyScheduleById(scheduleId);
        studyScheduleRepository.delete(studySchedule);
    }

    public StudyScheduleDto findSchedule(Long scheduleId) {
        StudySchedule studySchedule = findStudyScheduleById(scheduleId);
        return StudyScheduleDto.create(studySchedule);
    }

    public List<StudyScheduleDto> findAllSchedules(Long studyId) {
        List<StudySchedule> studySchedules = studyScheduleRepository.findAllByStudyId(studyId);
        return studySchedules.stream()
                .map(StudyScheduleDto::create)
                .collect(Collectors.toList());
    }

    private StudySchedule findStudyScheduleById(Long scheduleId) {
        return studyScheduleRepository.findById(scheduleId).orElseThrow(StudyScheduleNotFoundException::new);
    }
}
