package project.SangHyun.domain.service;

import project.SangHyun.dto.request.study.StudyJoinRequestDto;
import project.SangHyun.dto.response.study.StudyJoinResponseDto;

public interface StudyJoinService {
    StudyJoinResponseDto join(StudyJoinRequestDto requestDto);
}
