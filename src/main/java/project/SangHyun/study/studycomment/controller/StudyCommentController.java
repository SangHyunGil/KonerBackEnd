package project.SangHyun.study.studycomment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentDeleteResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentFindResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentUpdateResponseDto;
import project.SangHyun.study.studycomment.service.StudyCommentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/board/{boardId}/article/{articleId}/comment")
public class StudyCommentController {
    private final StudyCommentService studyCommentService;
    private final ResponseServiceImpl responseService;

    @GetMapping
    public MultipleResult<StudyCommentFindResponseDto> findAllComments(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                       @PathVariable Long articleId) {
        return responseService.getMultipleResult(studyCommentService.findComments(articleId));
    }

    @PostMapping
    public SingleResult<StudyCommentCreateResponseDto> createStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                         @PathVariable Long articleId,
                                                                         @Valid @RequestBody StudyCommentCreateRequestDto requestDto) {
        return responseService.getSingleResult(studyCommentService.createComment(articleId, requestDto));
    }

    @PutMapping("/{commentId}")
    public SingleResult<StudyCommentUpdateResponseDto> updateStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                          @PathVariable Long articleId, @PathVariable Long commentId,
                                                                          @Valid @RequestBody StudyCommentUpdateRequestDto requestDto) {
        return responseService.getSingleResult(studyCommentService.updateComment(commentId, requestDto));
    }

    @DeleteMapping("/{commentId}")
    public SingleResult<StudyCommentDeleteResponseDto> deleteStudyComment(@PathVariable Long studyId, @PathVariable Long boardId,
                                                                               @PathVariable Long articleId, @PathVariable Long commentId) {
        return responseService.getSingleResult(studyCommentService.deleteComment(commentId));
    }
}
