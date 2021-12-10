package project.SangHyun.domain.service;

import project.SangHyun.dto.request.study.StudyBoardCreateRequestDto;
import project.SangHyun.dto.request.study.StudyBoardUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.study.StudyBoardDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyBoardFindResponseDto;
import project.SangHyun.dto.response.study.StudyBoardUpdateResponseDto;

import java.util.List;

public interface StudyBoardService {
    List<StudyBoardFindResponseDto> findBoards(Long studyId);
    StudyBoardCreateResponseDto createBoard(Long studyId, StudyBoardCreateRequestDto requestDto);
    StudyBoardUpdateResponseDto updateBoard(Long memberId, Long studyId, Long studyBoardId, StudyBoardUpdateRequestDto requestDto);
    StudyBoardDeleteResponseDto deleteBoard(Long memberId, Long studyId, Long studyBoardId);
}
