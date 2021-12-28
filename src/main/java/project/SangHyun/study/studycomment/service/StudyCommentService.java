package project.SangHyun.study.studycomment.service;

import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentDeleteResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentFindResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentUpdateResponseDto;

import java.util.List;

public interface StudyCommentService {
    List<StudyCommentFindResponseDto> findComments(Long studyArticleId);
    StudyCommentCreateResponseDto createComment(Long studyArticleId, StudyCommentCreateRequestDto requestDto);
    StudyCommentUpdateResponseDto updateComment(Long studyCommentId, StudyCommentUpdateRequestDto requestDto);
    StudyCommentDeleteResponseDto deleteComment(Long studyCommentId);
}
