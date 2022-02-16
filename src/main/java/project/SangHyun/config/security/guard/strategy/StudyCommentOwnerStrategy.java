package project.SangHyun.config.security.guard.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;

@Component
@RequiredArgsConstructor
public class StudyCommentOwnerStrategy implements AuthStrategy {

    private final StudyCommentRepository studyCommentRepository;

    public boolean check(Long accessMemberId, Long commentId) {
        StudyComment studyComment = studyCommentRepository.findById(commentId).orElseThrow(() -> new AccessDeniedException(""));
        return studyComment.getCreatorId().equals(accessMemberId);
    }
}
