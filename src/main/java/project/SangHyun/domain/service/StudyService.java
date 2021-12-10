package project.SangHyun.domain.service;

import project.SangHyun.dto.request.study.StudyUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyCreateResponseDto;
import project.SangHyun.dto.request.study.StudyCreateRequestDto;
import project.SangHyun.dto.response.study.StudyDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyFindResponseDto;
import project.SangHyun.dto.response.study.StudyUpdateResponseDto;

import java.util.List;

public interface StudyService {
    StudyCreateResponseDto createStudy(StudyCreateRequestDto requestDto);
    List<StudyFindResponseDto> findAllStudies();
    StudyFindResponseDto findStudy(Long studyId);
    StudyUpdateResponseDto updateStudyInfo(Long memberId, Long studyId, StudyUpdateRequestDto requestDto);
    StudyDeleteResponseDto deleteStudy(Long memberId, Long studyId);
}
