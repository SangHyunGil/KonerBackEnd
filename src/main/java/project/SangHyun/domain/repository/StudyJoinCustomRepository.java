package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.Study;

import java.util.List;

public interface StudyJoinCustomRepository {
    Long findStudyMemberNum(Long boardId);
    List<Study> findStudyByMemberId(Long memberId);
}
