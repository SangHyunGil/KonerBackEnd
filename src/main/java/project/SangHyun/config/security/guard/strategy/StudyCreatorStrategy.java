package project.SangHyun.config.security.guard.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

@Component
@RequiredArgsConstructor
public class StudyCreatorStrategy implements AuthStrategy {

    private final StudyJoinRepository studyJoinRepository;

    public boolean check(Long accessMemberId, Long studyId) {
        StudyJoin studyJoin = studyJoinRepository.findStudyRole(accessMemberId, studyId).orElseThrow(() -> new AccessDeniedException(""));
        return studyJoin.getStudyRole().equals(StudyRole.CREATOR);
    }
}
