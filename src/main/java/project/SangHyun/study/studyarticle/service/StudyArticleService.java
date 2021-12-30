package project.SangHyun.study.studyarticle.service;

import project.SangHyun.common.dto.PageResponseDto;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;

public interface StudyArticleService {
    StudyArticleCreateResponseDto createArticle(Long boardId, StudyArticleCreateRequestDto requestDto);
    PageResponseDto findAllArticles(Long boardId, Integer page, Integer size);
    StudyArticleFindResponseDto findArticle(Long articleId);
    StudyArticleUpdateResponseDto updateArticle(Long articleId, StudyArticleUpdateRequestDto requestDto);
    StudyArticleDeleteResponseDto deleteArticle(Long articleId);
}
