package project.SangHyun.study.studyarticle.repository;

import project.SangHyun.study.studyarticle.domain.StudyArticle;

import java.util.List;

public interface StudyArticleCustomRepository {
    List<StudyArticle> findAllArticles(Long categoryId);
    List<StudyArticle> findArticleByTitle(String title);
}
