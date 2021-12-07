package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.Study;
import project.SangHyun.dto.response.StudyFindResponseDto;

import java.util.List;

public interface StudyCustomRepository {
    Study findStudyById(Long studyId);
}
