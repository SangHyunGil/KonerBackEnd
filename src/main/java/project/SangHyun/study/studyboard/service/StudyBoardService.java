package project.SangHyun.study.studyboard.service;

import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardDeleteResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardUpdateResponseDto;

import java.util.List;

public interface StudyBoardService {
    List<StudyBoardFindResponseDto> findAllBoards(Long studyId);
    StudyBoardCreateResponseDto createBoard(Long studyId, StudyBoardCreateRequestDto requestDto);
    StudyBoardUpdateResponseDto updateBoard(Long studyId, Long studyBoardId, StudyBoardUpdateRequestDto requestDto);
    StudyBoardDeleteResponseDto deleteBoard(Long studyId, Long studyBoardId);
}
