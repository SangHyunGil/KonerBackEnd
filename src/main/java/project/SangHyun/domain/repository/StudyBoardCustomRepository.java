package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.StudyBoard;

import java.util.List;

public interface StudyBoardCustomRepository {
    List<StudyBoard> findBoards(Long studyId);
}
