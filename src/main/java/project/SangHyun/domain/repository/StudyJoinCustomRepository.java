package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.Board;

import java.util.List;

public interface StudyJoinCustomRepository {
    Long findStudyMemberNum(Long boardId);
    List<Board> findStudyByMemberId(Long memberId);
}
