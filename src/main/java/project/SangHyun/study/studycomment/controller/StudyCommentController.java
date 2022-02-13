package project.SangHyun.study.studycomment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseService;
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

    @GetMapping
    public MultipleResult<StudyCommentResponseDto> findAllComments(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                   @PathVariable Long articleId) {
        List<StudyCommentDto> commentsDto = studyCommentService.findComments(articleId);
        return responseService.getMultipleResult(StudyCommentResponseDto.create(commentsDto));
    }

    @PostMapping
    public SingleResult<StudyCommentResponseDto> createStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                         @PathVariable Long articleId,
                                                                         @Valid @RequestBody StudyCommentCreateRequestDto requestDto) {
        StudyCommentDto commentDto = studyCommentService.createComment(articleId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyCommentResponseDto.create(commentDto));
    }

    @PutMapping("/{commentId}")
    public SingleResult<StudyCommentResponseDto> updateStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                          @PathVariable Long articleId, @PathVariable Long commentId,
                                                                          @Valid @RequestBody StudyCommentUpdateRequestDto requestDto) {
        StudyCommentDto commentDto = studyCommentService.updateComment(commentId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyCommentResponseDto.create(commentDto));
    }

    @DeleteMapping("/{commentId}")
    public Result deleteStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                     @PathVariable Long articleId, @PathVariable Long commentId) {
        studyCommentService.deleteComment(commentId);
        return responseService.getDefaultSuccessResult();
    }
}
