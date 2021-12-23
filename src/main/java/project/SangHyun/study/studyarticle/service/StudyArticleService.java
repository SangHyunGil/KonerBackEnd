package project.SangHyun.study.studyarticle.service;

import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;

import java.util.List;

public interface StudyArticleService {
    StudyArticleCreateResponseDto createArticle(Long boardId, StudyArticleCreateRequestDto requestDto);
    List<StudyArticleFindResponseDto> findAllArticles(Long boardId);
    StudyArticleFindResponseDto findArticle(Long articleId);
    StudyArticleUpdateResponseDto updateArticle(Long articleId, StudyArticleUpdateRequestDto requestDto);
    StudyArticleDeleteResponseDto deleteArticle(Long articleId);
}
