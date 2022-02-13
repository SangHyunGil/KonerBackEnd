package project.SangHyun.study.studyschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.study.studyschedule.domain.StudySchedule;

import java.util.List;

public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
    List<StudySchedule> findAllByStudyId(Long studyId);
}
