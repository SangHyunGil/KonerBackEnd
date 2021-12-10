package project.SangHyun.domain.service;

import project.SangHyun.dto.request.study.StudyArticleUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleCreateResponseDto;
import project.SangHyun.dto.request.study.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyArticleFindResponseDto;
import project.SangHyun.dto.response.study.StudyArticleUpdateResponseDto;

import java.util.List;

public interface StudyArticleService {
    StudyArticleCreateResponseDto createArticle(Long memberId, Long studyId, Long boardId, StudyArticleCreateRequestDto requestDto);
    List<StudyArticleFindResponseDto> findAllArticles(Long memberId, Long studyId, Long boardId);
    StudyArticleFindResponseDto findArticle(Long memberId, Long studyId, Long articleId);
    StudyArticleUpdateResponseDto updateArticle(Long memberId, Long studyId, Long articleId, StudyArticleUpdateRequestDto requestDto);
    StudyArticleDeleteResponseDto deleteArticle(Long memberId, Long studyId, Long articleId);
}
