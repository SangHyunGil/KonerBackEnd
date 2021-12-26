package project.SangHyun.study.studycomment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.study.studycomment.domain.StudyComment;

import java.util.List;

public interface StudyCommentRepository extends JpaRepository<StudyComment, Long> {
    List<StudyComment> findByMemberId(Long memberId);
}
