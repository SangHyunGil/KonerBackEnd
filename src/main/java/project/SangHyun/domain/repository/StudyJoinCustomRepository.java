package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.Study;

import java.util.List;

public interface StudyJoinCustomRepository {
    Long findStudyJoinCount(Long studyId);
    List<Study> findStudyByMemberId(Long memberId);
}
