package project.SangHyun.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.domain.entity.StudyJoin;

public interface StudyJoinRepository extends JpaRepository<StudyJoin, Long>, StudyJoinCustomRepository {
}
