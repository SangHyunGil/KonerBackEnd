package project.SangHyun.domain.service;

import project.SangHyun.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.dto.response.StudyBoardUpdateResponseDto;

import java.util.List;

public interface StudyBoardService {
    List<StudyBoardFindResponseDto> findBoards(Long studyId);
    StudyBoardCreateResponseDto createBoard(Long studyId, StudyBoardCreateRequestDto requestDto);
    StudyBoardUpdateResponseDto updateBoard(Long studyId, Long studyBoardId, StudyBoardUpdateRequestDto requestDto);
}
