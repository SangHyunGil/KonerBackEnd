package project.SangHyun.study.studyboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;

public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long>, StudyBoardCustomRepository {
}
