package project.SangHyun.study.studycomment.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.controller.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.controller.dto.response.StudyCommentResponseDto;
import project.SangHyun.study.studycomment.service.StudyCommentService;
import project.SangHyun.study.studycomment.service.dto.response.StudyCommentDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/board/{boardId}/article/{articleId}/comment")
public class StudyCommentController {

    private final StudyCommentService studyCommentService;
    private final ResponseService responseService;

    @ApiOperation(value = "스터디 댓글 조회", notes = "스터디에 포함된 게시글의 모든 댓글을 조회한다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<StudyCommentResponseDto> findAllComments(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                   @PathVariable Long articleId) {
        List<StudyCommentDto> commentsDto = studyCommentService.findComments(articleId);
        return responseService.getMultipleResult(StudyCommentResponseDto.create(commentsDto));
    }

    @ApiOperation(value = "스터디 댓글 생성", notes = "스터디에 포함된 게시글의 댓글을 생성한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResult<StudyCommentResponseDto> createStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                         @PathVariable Long articleId,
                                                                         @Valid @RequestBody StudyCommentCreateRequestDto requestDto) {
        StudyCommentDto commentDto = studyCommentService.createComment(articleId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyCommentResponseDto.create(commentDto));
    }

    @ApiOperation(value = "스터디 댓글 수정", notes = "스터디에 포함된 게시글의 댓글을 수정한다.")
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<StudyCommentResponseDto> updateStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                          @PathVariable Long articleId, @PathVariable Long commentId,
                                                                          @Valid @RequestBody StudyCommentUpdateRequestDto requestDto) {
        StudyCommentDto commentDto = studyCommentService.updateComment(commentId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyCommentResponseDto.create(commentDto));
    }

    @ApiOperation(value = "스터디 댓글 삭제", notes = "스터디에 포함된 게시글의 댓글을 삭제한다.")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                     @PathVariable Long articleId, @PathVariable Long commentId) {
        studyCommentService.deleteComment(commentId);
        return responseService.getDefaultSuccessResult();
    }
}
