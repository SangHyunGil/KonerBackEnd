package project.SangHyun.study.studyboard.repository;

import project.SangHyun.study.studyboard.domain.StudyBoard;

import java.util.List;

public interface StudyBoardCustomRepository {
    List<StudyBoard> findBoards(Long studyId);
}
