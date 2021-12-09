package project.SangHyun.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.domain.entity.QStudy;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.repository.StudyJoinCustomRepository;

import java.util.List;

import static project.SangHyun.domain.entity.QStudy.study;
import static project.SangHyun.domain.entity.QStudyJoin.studyJoin;

@RequiredArgsConstructor
public class StudyJoinCustomRepositoryImpl implements StudyJoinCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findStudyJoinCount(Long studyId) {
        return jpaQueryFactory
                .select(study)
                .from(studyJoin)
                .where(studyJoin.study.id.eq(studyId))
                .fetchCount();
    }

    @Override
    public List<Study> findStudyByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(study)
                .from(studyJoin)
                .where(studyJoin.member.id.eq(memberId))
                .fetch();
    }
}
