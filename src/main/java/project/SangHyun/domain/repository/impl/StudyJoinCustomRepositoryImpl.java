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
    public Long findStudyMemberNum(Long boardId) {
        return jpaQueryFactory
                .selectFrom(study)
                .where(studyJoin.study.id.eq(boardId))
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
