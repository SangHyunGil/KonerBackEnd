package project.SangHyun.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.domain.entity.StudyJoin;

import java.util.Optional;

public interface StudyJoinRepository extends JpaRepository<StudyJoin, Long>, StudyJoinCustomRepository {
    Optional<StudyJoin> findByMemberIdAndStudyId(Long memberId, Long studyId);
}
