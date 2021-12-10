package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.repository.impl.dto.StudyInfoDto;

import java.util.List;

public interface StudyJoinCustomRepository {
    Long findStudyJoinCount(Long studyId);
    List<StudyInfoDto> findStudyInfoByMemberId(Long memberId);
    Boolean exist(Long studyId, Long memberId);
}
