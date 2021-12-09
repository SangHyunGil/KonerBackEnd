package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.Study;

import java.util.List;

public interface StudyCustomRepository {
    Study findStudyById(Long studyId);
    List<Study> findStudyByTitle(String title);
}
