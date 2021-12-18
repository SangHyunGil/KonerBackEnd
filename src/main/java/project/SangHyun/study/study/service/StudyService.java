package project.SangHyun.study.study.service;

import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.response.StudyDeleteResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.dto.response.StudyUpdateResponseDto;

import java.util.List;

public interface StudyService {
    StudyCreateResponseDto createStudy(StudyCreateRequestDto requestDto);
    List<StudyFindResponseDto> findAllStudies();
    StudyFindResponseDto findStudy(Long studyId);
    StudyUpdateResponseDto updateStudy(Long studyId, StudyUpdateRequestDto requestDto);
    StudyDeleteResponseDto deleteStudy(Long studyId);
}
