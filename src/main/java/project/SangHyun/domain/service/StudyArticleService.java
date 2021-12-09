package project.SangHyun.domain.service;

import project.SangHyun.domain.response.StudyArticleCreateResponseDto;
import project.SangHyun.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.StudyArticleFindResponseDto;

import java.util.List;

public interface StudyArticleService {
    StudyArticleCreateResponseDto createArticle(StudyArticleCreateRequestDto requestDto, Long boardId);
    List<StudyArticleFindResponseDto> findAllArticles(Long boardId);
}
