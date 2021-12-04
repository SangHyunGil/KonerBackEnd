package project.SangHyun.domain.service;

import project.SangHyun.dto.response.BoardCreateResponseDto;
import project.SangHyun.dto.request.BoardCreateRequestDto;
import project.SangHyun.dto.response.BoardFindResponseDto;

import java.util.List;

public interface BoardService {
    BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto);
    List<BoardFindResponseDto> findAllBoards();
    BoardFindResponseDto findBoard(Long boardId);
}
