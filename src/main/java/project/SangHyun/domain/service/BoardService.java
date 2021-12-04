package project.SangHyun.domain.service;

import project.SangHyun.dto.request.BoardUpdateRequestDto;
import project.SangHyun.dto.response.BoardCreateResponseDto;
import project.SangHyun.dto.request.BoardCreateRequestDto;
import project.SangHyun.dto.response.BoardFindResponseDto;
import project.SangHyun.dto.response.BoardUpdateResponseDto;

import java.util.List;

public interface BoardService {
    BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto);
    List<BoardFindResponseDto> findAllBoards();
    BoardFindResponseDto findBoard(Long boardId);
    BoardUpdateResponseDto updateBoardInfo(BoardUpdateRequestDto requestDto);
}
