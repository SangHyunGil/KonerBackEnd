package project.SangHyun.study.studycomment.service;

import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;

public interface StudyCommentService {
    StudyCommentCreateResponseDto createComment(Long studyArticleId, StudyCommentCreateRequestDto requestDto);
}
