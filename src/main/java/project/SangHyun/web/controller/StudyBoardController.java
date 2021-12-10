package project.SangHyun.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.domain.service.StudyBoardService;
import project.SangHyun.dto.request.study.StudyBoardCreateRequestDto;
import project.SangHyun.dto.request.study.StudyBoardUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.study.StudyBoardDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyBoardFindResponseDto;
import project.SangHyun.dto.response.study.StudyBoardUpdateResponseDto;

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

    @ApiOperation(value = "스터디 게시판 수정", notes = "스터디에 포함된 게시판을 수정한다.")
    @PutMapping("/{boardId}")
    public SingleResult<StudyBoardUpdateResponseDto> updateStudyBoard(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                      @PathVariable Long studyId, @PathVariable Long boardId,
                                                                      @Valid @RequestBody StudyBoardUpdateRequestDto requestDto) {
        return responseService.getSingleResult(studyBoardService.updateBoard(memberDetails.getId(), studyId, boardId, requestDto));
    }

    @ApiOperation(value = "스터디 게시판 삭제", notes = "스터디에 포함된 게시판을 삭제한다.")
    @DeleteMapping("/{boardId}")
    public SingleResult<StudyBoardDeleteResponseDto> deleteStudyBoard(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                      @PathVariable Long studyId, @PathVariable Long boardId) {
        return responseService.getSingleResult(studyBoardService.deleteBoard(memberDetails.getId(), studyId, boardId));
    }
}
