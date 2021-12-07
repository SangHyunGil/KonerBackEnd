package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.Study;

public interface StudyCustomRepository {
    Study findStudyById(Long studyId);
}
