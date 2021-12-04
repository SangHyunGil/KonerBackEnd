package project.SangHyun.domain.service;

import project.SangHyun.dto.request.StudyJoinRequestDto;
import project.SangHyun.dto.response.StudyJoinResponseDto;

public interface StudyJoinService {
    StudyJoinResponseDto join(StudyJoinRequestDto requestDto);
}
