package project.SangHyun.study.study.service;

import project.SangHyun.common.dto.SliceResponseDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.*;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;

import java.io.IOException;
import java.util.List;

public interface StudyService {
    StudyCreateResponseDto createStudy(StudyCreateRequestDto requestDto) throws IOException;
    SliceResponseDto findAllStudiesByDepartment(Long lastStudyId, String department, Integer size);
    StudyFindResponseDto findStudy(Long studyId);
    StudyUpdateResponseDto updateStudy(Long studyId, StudyUpdateRequestDto requestDto) throws IOException;
    StudyDeleteResponseDto deleteStudy(Long studyId);
}
