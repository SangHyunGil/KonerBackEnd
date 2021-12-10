package project.SangHyun.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.StudyBoardService;
import project.SangHyun.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.StudyBoardFindResponseDto;

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
        return responseService.getMultipleResult(studyBoardService.findBoards(studyId));
    }

    @ApiOperation(value = "스터디 게시판 생성", notes = "스터디에 포함된 게시판을 생성한다.")
    @PostMapping
    public SingleResult<StudyBoardCreateResponseDto> createStudyBoard(@PathVariable Long studyId,
                                                                      @Valid @RequestBody StudyBoardCreateRequestDto requestDto) {
        return responseService.getSingleResult(studyBoardService.createBoard(studyId, requestDto));
    }
}
