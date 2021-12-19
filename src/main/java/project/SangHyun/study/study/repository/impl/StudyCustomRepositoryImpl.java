package project.SangHyun.study.study.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyCustomRepository;
import java.util.List;

import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.study.study.domain.QStudy.*;
import static project.SangHyun.study.studyjoin.domain.QStudyJoin.studyJoin;

@Slf4j
@RequiredArgsConstructor
public class StudyCustomRepositoryImpl implements StudyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Study findStudyById(Long studyId) {
        return jpaQueryFactory.selectFrom(study)
                .innerJoin(study.member, member).fetchJoin()
                .innerJoin(study.studyJoins, studyJoin).fetchJoin()
                .innerJoin(studyJoin.member, member).fetchJoin()
                .where(study.id.eq(studyId))
                .distinct()
                .fetchOne();
    }

    @Override
    public List<Study> findStudyByTitle(String title) {
        return jpaQueryFactory.selectFrom(study)
                .where(study.title.contains(title))
                .fetch();
    }
}
