package project.SangHyun.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.domain.entity.StudyArticle;

public interface StudyArticleRepository extends JpaRepository<StudyArticle, Long>, StudyArticleCustomRepository {
}
