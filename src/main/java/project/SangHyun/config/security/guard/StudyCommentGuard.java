package project.SangHyun.config.security.guard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

@Component
@RequiredArgsConstructor
public class StudyCommentGuard {
    private final AuthHelper authHelper;
    private final StudyJoinRepository studyJoinRepository;
    private final StudyCommentRepository studyCommentRepository;

    public boolean checkJoin(Long studyId) {
        return authHelper.isAuthenticated() && isJoinMember(studyId);
    }

    public boolean isJoinMember(Long studyId) {
        return (isMember() && isStudyMember(studyId)) || isAdmin();
    }

    private boolean isStudyMember(Long studyId) {
        return studyJoinRepository.exist(studyId, authHelper.extractMemberId());
    }

    public boolean checkJoinAndAuth(Long studyId, Long articleId) {
        return authHelper.isAuthenticated() && hasAuthority(studyId, articleId);
    }

    private boolean hasAuthority(Long studyId, Long commentId) {
        return (isMember() && isStudyMember(studyId) && hasResourceAuthority(studyId, commentId)) || isAdmin();
    }

    private boolean hasResourceAuthority(Long studyId, Long commentId) {
        return isStudyCommentOwner(commentId) || isStudyAdminOrCreator(studyId);
    }

    private boolean isStudyCommentOwner(Long commentId) {
        StudyComment studyComment = studyCommentRepository.findById(commentId).orElseThrow(() -> new AccessDeniedException(""));
        return studyComment.getMember().getId().equals(authHelper.extractMemberId());
    }

    private boolean isStudyAdminOrCreator(Long studyId) {
        StudyJoin studyJoin = studyJoinRepository.findStudyRole(authHelper.extractMemberId(), studyId).orElseThrow(() -> new AccessDeniedException(""));
        return studyJoin.getStudyRole().equals(StudyRole.ADMIN) || studyJoin.getStudyRole().equals(StudyRole.CREATOR);
    }

    private Boolean isMember() {
        return authHelper.extractMemberRole().equals("ROLE_MEMBER");
    }

    private Boolean isAdmin() {
        return authHelper.extractMemberRole().equals("ROLE_ADMIN");
    }
}
