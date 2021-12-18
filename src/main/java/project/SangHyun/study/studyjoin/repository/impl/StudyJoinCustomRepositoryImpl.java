package project.SangHyun.study.studyjoin.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.domain.entity.QStudyJoin;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinCustomRepository;

import java.util.List;
import java.util.Optional;

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
    public List<StudyInfoDto> findStudyInfoByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(Projections.constructor(StudyInfoDto.class,
                        studyJoin.study.id, studyJoin.studyRole))
                .from(studyJoin)
                .join(studyJoin.study, study)
                .where(studyJoin.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public Boolean exist(Long studyId, Long memberId) {
        StudyJoin studyJoin = jpaQueryFactory
                .selectFrom(QStudyJoin.studyJoin)
                .where(QStudyJoin.studyJoin.study.id.eq(studyId),
                        QStudyJoin.studyJoin.member.id.eq(memberId))
                .fetchFirst();

        return studyJoin != null;
    }

    @Override
    public Optional<StudyJoin> findStudyRole(Long memberId, Long studyId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(studyJoin)
                .where(studyJoin.study.id.eq(studyId),
                        studyJoin.member.id.eq(memberId))
                .fetchOne());
    }
}
