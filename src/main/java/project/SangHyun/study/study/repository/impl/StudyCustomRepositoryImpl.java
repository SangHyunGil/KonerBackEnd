package project.SangHyun.study.study.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyCustomRepository;

import java.util.List;

import static project.SangHyun.common.helper.BooleanBuilderHelper.nullSafeBuilder;
import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.study.study.domain.QStudy.study;

@Slf4j
@RequiredArgsConstructor
public class StudyCustomRepositoryImpl implements StudyCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Study findStudyById(Long studyId) {
        return jpaQueryFactory.selectFrom(study)
                .innerJoin(study.member, member).fetchJoin()
                .where(equalsStudyId(studyId))
                .distinct()
                .fetchOne();
    }

    private BooleanBuilder equalsStudyId(Long studyId) {
        return nullSafeBuilder(() -> study.id.eq(studyId));
    }

    @Override
    public List<Study> findStudyByTitle(String title) {
        return jpaQueryFactory.selectFrom(study)
                .where(equalsStudyTitle(title))
                .fetch();
    }

    private BooleanBuilder equalsStudyTitle(String title) {
        return nullSafeBuilder(() -> study.title.title.contains(title));
    }
}
