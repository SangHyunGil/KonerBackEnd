package project.SangHyun.study.studyboard.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.studyboard.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardDeleteResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.study.studyboard.dto.response.StudyBoardUpdateResponseDto;
import project.SangHyun.study.studyboard.service.StudyBoardService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/board")
public class StudyBoardController {

    private final StudyBoardService studyBoardService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "스터디 게시판 로드", notes = "스터디에 포함된 게시판의 목록을 로드한다.")
    @GetMapping
    public MultipleResult<StudyBoardFindResponseDto> findStudyBoards(@PathVariable Long studyId) {
        return responseService.getMultipleResult(studyBoardService.findAllBoards(studyId));
    }

    @ApiOperation(value = "스터디 게시판 생성", notes = "스터디에 포함된 게시판을 생성한다.")
    @PostMapping
    public SingleResult<StudyBoardCreateResponseDto> createStudyBoard(@PathVariable Long studyId,
                                                                      @Valid @RequestBody StudyBoardCreateRequestDto requestDto) {
        return responseService.getSingleResult(studyBoardService.createBoard(studyId, requestDto));
    }

    @ApiOperation(value = "스터디 게시판 수정", notes = "스터디에 포함된 게시판을 수정한다.")
    @PutMapping("/{boardId}")
    public SingleResult<StudyBoardUpdateResponseDto> updateStudyBoard( @PathVariable Long studyId, @PathVariable Long boardId,
                                                                      @Valid @RequestBody StudyBoardUpdateRequestDto requestDto) {
        return responseService.getSingleResult(studyBoardService.updateBoard(studyId, boardId, requestDto));
    }

    @ApiOperation(value = "스터디 게시판 삭제", notes = "스터디에 포함된 게시판을 삭제한다.")
    @DeleteMapping("/{boardId}")
    public SingleResult<StudyBoardDeleteResponseDto> deleteStudyBoard(@PathVariable Long studyId, @PathVariable Long boardId) {
        return responseService.getSingleResult(studyBoardService.deleteBoard(studyId, boardId));
    }
}
