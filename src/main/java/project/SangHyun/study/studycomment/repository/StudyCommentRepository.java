package project.SangHyun.study.studycomment.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.study.studycomment.domain.StudyComment;

import java.util.List;
import java.util.Optional;

public interface StudyCommentRepository extends JpaRepository<StudyComment, Long> {
    @EntityGraph(attributePaths = {"member"})
    List<StudyComment> findByMemberId(Long memberId);
    @EntityGraph(attributePaths = {"member"})
    Optional<StudyComment> findById(Long id);
}
