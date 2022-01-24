package project.SangHyun.study.studyjoin.service;

import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.FindJoinedStudyResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;

import java.util.List;

public interface StudyJoinService {
    StudyJoinResponseDto applyJoin(Long studyId, Long memberId, StudyJoinRequestDto requestDto);
    StudyJoinResponseDto acceptJoin(Long studyId, Long memberId);
    List<StudyFindMembersResponseDto> findStudyMembers(Long studyId);
    StudyJoinResponseDto rejectJoin(Long studyId, Long memberId);
    List<FindJoinedStudyResponseDto> findJoinedStudies(Long memberId);
}
