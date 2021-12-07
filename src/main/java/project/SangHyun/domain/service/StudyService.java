package project.SangHyun.domain.service;

import project.SangHyun.dto.request.StudyUpdateRequestDto;
import project.SangHyun.dto.response.StudyCreateResponseDto;
import project.SangHyun.dto.request.StudyCreateRequestDto;
import project.SangHyun.dto.response.StudyFindResponseDto;
import project.SangHyun.dto.response.StudyUpdateResponseDto;

import java.util.List;

public interface StudyService {
    StudyCreateResponseDto createStudy(StudyCreateRequestDto requestDto);
    List<StudyFindResponseDto> findAllStudies();
    StudyFindResponseDto findStudy(Long studyId);
    StudyUpdateResponseDto updateStudyInfo(StudyUpdateRequestDto requestDto);
}
