package project.SangHyun.config.security.guard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

@Component
@RequiredArgsConstructor
public class StudyJoinGuard {
    private final AuthHelper authHelper;
    private final StudyJoinRepository studyJoinRepository;

    public boolean check(Long studyId) {
        return authHelper.isAuthenticated() && hasAuthority(studyId);
    }

    private boolean hasAuthority(Long studyId) {
        return (isMember() && isStudyAdminOrCreator(studyId)) || isAdmin();
    }

    private boolean isMember() {
        return authHelper.extractMemberRole().equals("ROLE_MEMBER");
    }

    private boolean isStudyAdminOrCreator(Long studyId) {
        StudyJoin studyJoin = studyJoinRepository.findStudyRole(authHelper.extractMemberId(), studyId).orElseThrow(() -> new AccessDeniedException(""));
        return studyJoin.getStudyRole().equals(StudyRole.ADMIN) ||
                studyJoin.getStudyRole().equals(StudyRole.CREATOR);
    }

    private boolean isAdmin() {
        return authHelper.extractMemberRole().equals("ROLE_ADMIN");
    }
}
