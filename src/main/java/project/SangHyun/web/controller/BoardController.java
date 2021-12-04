package project.SangHyun.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.BoardService;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.dto.request.BoardCreateRequestDto;
import project.SangHyun.dto.response.BoardCreateResponseDto;
import project.SangHyun.dto.response.BoardFindResponseDto;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final ResponseServiceImpl responseService;

    @GetMapping
    public MultipleResult<BoardFindResponseDto> findAllBoards() {
        return responseService.getMultipleResult(boardService.findAllBoards());
    }

    @GetMapping("/{boardId}")
    public SingleResult<BoardFindResponseDto> findBoard(@PathVariable Long boardId) {
        return responseService.getSingleResult(boardService.findBoard(boardId));
    }

    @PostMapping
    public SingleResult<BoardCreateResponseDto> createBoard(@RequestBody BoardCreateRequestDto requestDto) {
        log.info("createBoard = {}, {}, {}, {}, {}, {}", requestDto.getMemberId(), requestDto.getHeadCount(), requestDto.getRecruitState(), requestDto.getTitle(), requestDto.getTopic(), requestDto.getStudyState());
        return responseService.getSingleResult(boardService.createBoard(requestDto));
    }
}
