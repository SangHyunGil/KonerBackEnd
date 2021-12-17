package project.SangHyun.study.study.repository;

import project.SangHyun.study.study.domain.Study;

import java.util.List;

public interface StudyCustomRepository {
    Study findStudyById(Long studyId);
    List<Study> findStudyByTitle(String title);
}
