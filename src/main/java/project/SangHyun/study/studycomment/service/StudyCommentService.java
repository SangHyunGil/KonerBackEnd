package project.SangHyun.study.studycomment.service;

import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentUpdateResponseDto;

public interface StudyCommentService {
    StudyCommentCreateResponseDto createComment(Long studyArticleId, StudyCommentCreateRequestDto requestDto);
    StudyCommentUpdateResponseDto updateComment(Long studyCommentId, StudyCommentUpdateRequestDto requestDto);
}
