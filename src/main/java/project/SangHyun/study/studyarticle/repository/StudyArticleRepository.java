package project.SangHyun.study.studyarticle.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

import java.util.Optional;

public interface StudyArticleRepository extends JpaRepository<StudyArticle, Long>, StudyArticleCustomRepository {
    @EntityGraph(attributePaths = {"member"})
    Optional<StudyArticle> findById(Long id);
}
