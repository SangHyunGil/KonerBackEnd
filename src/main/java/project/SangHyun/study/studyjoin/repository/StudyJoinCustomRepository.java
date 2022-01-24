package project.SangHyun.study.studyjoin.repository;

import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;

import java.util.List;
import java.util.Optional;

public interface StudyJoinCustomRepository {
    Long findStudyJoinCount(Long studyId);
    Boolean exist(Long studyId, Long memberId);
    Optional<StudyJoin> findStudyRole(Long memberId, Long studyId);
    List<StudyMembersInfoDto> findStudyMembers(Long studyId);
    List<Study> findStudiesByMemberId(Long memberId);
    Optional<StudyJoin> findApplyStudy(Long studyId, Long memberId);
    List<StudyJoin> findAdminAndCreator(Long studyId);
}
