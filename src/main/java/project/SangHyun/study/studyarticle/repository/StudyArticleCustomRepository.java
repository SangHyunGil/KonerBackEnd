package project.SangHyun.study.studyarticle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

import java.util.List;

public interface StudyArticleCustomRepository {
    Page<StudyArticle> findAllOrderByStudyArticleIdDesc(Long boardId, Pageable pageable);
    List<StudyArticle> findArticleByTitle(String title);
}
