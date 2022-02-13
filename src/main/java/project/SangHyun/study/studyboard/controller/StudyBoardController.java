package project.SangHyun.study.studyboard.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.study.studyboard.controller.dto.response.StudyBoardResponseDto;
import project.SangHyun.study.studyboard.service.StudyBoardService;
import project.SangHyun.study.studyboard.service.dto.response.StudyBoardDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/board")
public class StudyBoardController {

    private final StudyBoardService studyBoardService;
    private final ResponseService responseService;

    @ApiOperation(value = "스터디 게시판 로드", notes = "스터디에 포함된 게시판의 목록을 로드한다.")
    @GetMapping
    public MultipleResult<StudyBoardResponseDto> findStudyBoards(@PathVariable Long studyId) {
        return responseService.getMultipleResult(convertToStudyBoardResponseDto(studyBoardService.findAllBoards(studyId)));
    }

    @ApiOperation(value = "스터디 게시판 생성", notes = "스터디에 포함된 게시판을 생성한다.")
    @PostMapping
    public SingleResult<StudyBoardResponseDto> createStudyBoard(@PathVariable Long studyId,
                                                                      @Valid @RequestBody StudyBoardCreateRequestDto requestDto) {
        StudyBoardDto studyBoardDto = studyBoardService.createBoard(studyId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyBoardResponseDto.create(studyBoardDto));
    }

    @ApiOperation(value = "스터디 게시판 수정", notes = "스터디에 포함된 게시판을 수정한다.")
    @PutMapping("/{boardId}")
    public SingleResult<StudyBoardResponseDto> updateStudyBoard( @PathVariable Long studyId, @PathVariable Long boardId,
                                                                      @Valid @RequestBody StudyBoardUpdateRequestDto requestDto) {
        StudyBoardDto studyBoardDto = studyBoardService.updateBoard(boardId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyBoardResponseDto.create(studyBoardDto));
    }

    @ApiOperation(value = "스터디 게시판 삭제", notes = "스터디에 포함된 게시판을 삭제한다.")
    @DeleteMapping("/{boardId}")
    public Result deleteStudyBoard(@PathVariable Long studyId, @PathVariable Long boardId) {
        studyBoardService.deleteBoard(boardId);
        return responseService.getDefaultSuccessResult();
    }

    private List<StudyBoardResponseDto> convertToStudyBoardResponseDto(List<StudyBoardDto> studyBoardDto) {
        return studyBoardDto.stream()
                .map(StudyBoardResponseDto::create)
                .collect(Collectors.toList());
    }
}
