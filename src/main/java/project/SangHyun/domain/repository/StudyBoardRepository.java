package project.SangHyun.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.domain.entity.StudyBoard;

public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long>, StudyBoardCustomRepository {
}
