package project.SangHyun.study.studyjoin.service;

import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;

import java.util.List;

public interface StudyJoinService {
    StudyJoinResponseDto joinStudy(StudyJoinRequestDto requestDto);
    List<StudyFindMembersResponseDto> findStudyMembers(Long studyId);
}
