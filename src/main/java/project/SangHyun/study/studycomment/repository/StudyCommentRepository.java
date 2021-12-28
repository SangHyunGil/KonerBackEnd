package project.SangHyun.study.studycomment.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.SangHyun.study.studycomment.domain.StudyComment;

import java.util.List;
import java.util.Optional;

public interface StudyCommentRepository extends JpaRepository<StudyComment, Long> {
    @EntityGraph(attributePaths = {"member"})
    List<StudyComment> findAllByMemberId(Long memberId);
    @EntityGraph(attributePaths = {"member"})
    Optional<StudyComment> findById(Long id);
    @Query("select sc from StudyComment sc left join fetch sc.parent where sc.studyArticle.id = :studyArticleId order by sc.parent.id asc nulls first, sc.id asc")
    List<StudyComment> findAllByStudyArticleId(@Param("studyArticleId") Long studyArticleId);
}
