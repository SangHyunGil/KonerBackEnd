package project.SangHyun.domain.repository;

import project.SangHyun.domain.entity.StudyArticle;

import java.util.List;

public interface StudyArticleCustomRepository {
    List<StudyArticle> findAllArticles(Long categoryId);
}
