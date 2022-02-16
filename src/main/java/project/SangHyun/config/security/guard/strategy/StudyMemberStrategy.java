package project.SangHyun.config.security.guard.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;

@Component
@RequiredArgsConstructor
public class StudyMemberStrategy implements AuthStrategy {

    private final StudyJoinRepository studyJoinRepository;

    @Override
    public boolean check(Long accessMemberId, Long studyId) {
        return studyJoinRepository.isStudyMember(studyId, accessMemberId);
    }
}
