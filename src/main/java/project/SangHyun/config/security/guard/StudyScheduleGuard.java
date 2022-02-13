package project.SangHyun.config.security.guard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

@Component
@RequiredArgsConstructor
public class StudyScheduleGuard {

    private final AuthHelper authHelper;
    private final StudyJoinRepository studyJoinRepository;

    public boolean checkJoin(Long studyId) {
        return authHelper.isAuthenticated() && isJoinMember(studyId);
    }

    public boolean isJoinMember(Long studyId) {
        return (isMember() && isStudyMember(studyId)) || isAdmin();
    }

    private boolean isStudyMember(Long studyId) {
        return studyJoinRepository.exist(studyId, authHelper.extractMemberId());
    }

    private Boolean isMember() {
        return authHelper.extractMemberRole().equals("ROLE_MEMBER");
    }

    private Boolean isAdmin() {
        return authHelper.extractMemberRole().equals("ROLE_ADMIN");
    }
}
