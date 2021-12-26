package project.SangHyun.study.studycomment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.study.studycomment.domain.StudyComment;

public interface StudyCommentRepository extends JpaRepository<StudyComment, Long> {
}
