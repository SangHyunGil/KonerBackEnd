package project.SangHyun.study.study.service;

import project.SangHyun.common.dto.SliceResponseDto;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyDeleteResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.dto.response.StudyUpdateResponseDto;

import java.io.IOException;

public interface StudyService {
    StudyCreateResponseDto createStudy(StudyCreateRequestDto requestDto) throws IOException;
    SliceResponseDto findAllStudiesByDepartment(Long lastStudyId, StudyCategory category, Integer size);
    StudyFindResponseDto findStudy(Long studyId);
    StudyUpdateResponseDto updateStudy(Long studyId, StudyUpdateRequestDto requestDto) throws IOException;
    StudyDeleteResponseDto deleteStudy(Long studyId);
}
