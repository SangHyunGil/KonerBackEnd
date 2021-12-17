package project.SangHyun.study.studyjoin.service;

import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;

public interface StudyJoinService {
    StudyJoinResponseDto joinStudy(StudyJoinRequestDto requestDto);
}
