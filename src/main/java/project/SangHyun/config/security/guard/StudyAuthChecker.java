package project.SangHyun.config.security.guard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import project.SangHyun.config.security.guard.strategy.AuthStrategy;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

@RequiredArgsConstructor
public class StudyAuthChecker {

    private final StudyJoinRepository studyJoinRepository;
    private final AuthHelper authHelper;
    private final AuthStrategy authStrategy;

    public boolean check(Long studyId, Long resourceId) {
        Long accessMemberId = authHelper.extractMemberId();

        // 인증을 받은 유저 중, 웹 관리자이거나 정규 회원이면서 스터디 관리자이거나 리소스 주인인지 체크
        return authHelper.isAuthenticated() &&
               (authHelper.isAdmin() || (authHelper.isRegularMember() && isStudyCreatorOrAdminOrResourceOwner(accessMemberId, studyId, resourceId)));
    }

    private boolean isStudyCreatorOrAdminOrResourceOwner(Long accessMemberId, Long studyId, Long resourceId) {
        return isStudyCreatorOrAdmin(studyId) || (isStudyMember(studyId) && authStrategy.check(accessMemberId, resourceId));
    }

    private boolean isStudyCreatorOrAdmin(Long studyId) {
        StudyJoin studyJoin = studyJoinRepository.findStudyRole(authHelper.extractMemberId(), studyId).orElseThrow(() -> new AccessDeniedException(""));
        return studyJoin.getStudyRole().equals(StudyRole.ADMIN) || studyJoin.getStudyRole().equals(StudyRole.CREATOR);
    }

    private boolean isStudyMember(Long studyId) {
        return studyJoinRepository.isStudyMember(studyId, authHelper.extractMemberId());
    }
}
