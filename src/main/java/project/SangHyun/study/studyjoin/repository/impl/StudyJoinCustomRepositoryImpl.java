package project.SangHyun.study.studyjoin.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinCustomRepository;

import java.util.List;
import java.util.Optional;

import static project.SangHyun.helper.BooleanBuilderHelper.nullSafeBuilder;
import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.study.study.domain.QStudy.study;
import static project.SangHyun.study.studyjoin.domain.QStudyJoin.studyJoin;

@RequiredArgsConstructor
public class StudyJoinCustomRepositoryImpl implements StudyJoinCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findStudyJoinCount(Long studyId) {
        return jpaQueryFactory
                .select(study)
                .from(studyJoin)
                .where(equalsStudyId(studyId),
                        isStudyMember())
                .fetchCount();
    }

    @Override
    public Boolean isStudyMember(Long studyId, Long memberId) {
        StudyJoin findStudyJoin = jpaQueryFactory
                .selectFrom(studyJoin)
                .where(equalsStudyId(studyId),
                        equalsMemberId(memberId),
                        isStudyMember())
                .fetchFirst();

        return findStudyJoin != null;
    }

    @Override
    public Optional<StudyJoin> findStudyRole(Long memberId, Long studyId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(studyJoin)
                .where(equalsStudyId(studyId),
                        equalsMemberId(memberId))
                .fetchOne());
    }

    @Override
    public List<StudyMembersInfoDto> findStudyMembers(Long studyId) {
        return jpaQueryFactory
                .select(Projections.constructor(StudyMembersInfoDto.class,
                        member.id, member.nickname.nickname, member.profileImgUrl.profileImgUrl,
                        studyJoin.studyRole, studyJoin.applyContent.applyContent))
                .from(studyJoin)
                .innerJoin(studyJoin.member, member)
                .where(equalsStudyId(studyId))
                .fetch();
    }

    @Override
    public List<Study> findStudiesByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(study)
                .from(studyJoin)
                .innerJoin(studyJoin.study, study)
                .where(equalsMemberId(memberId),
                        isStudyMember())
                .fetch();
    }

    @Override
    public Optional<StudyJoin> findApplyStudy(Long studyId, Long memberId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(studyJoin)
                .innerJoin(studyJoin.member, member).fetchJoin()
                .innerJoin(studyJoin.study, study).fetchJoin()
                .where(equalsStudyId(studyId),
                        equalsMemberId(memberId))
                .fetchFirst());
    }

    @Override
    public List<StudyJoin> findAdminAndCreator(Long studyJoinId) {
        return jpaQueryFactory.select(studyJoin)
                .from(studyJoin)
                .innerJoin(studyJoin.member, member).fetchJoin()
                .innerJoin(studyJoin.study, study).fetchJoin()
                .where(equalsStudyJoinId(studyJoinId),
                       isStudyCreatorOrAdmin())
                .fetch();
    }

    private BooleanBuilder equalsStudyId(Long studyId) {
        return nullSafeBuilder(() -> studyJoin.study.id.eq(studyId));
    }

    private BooleanBuilder isStudyMember() {
        return nullSafeBuilder(() ->  studyJoin.studyRole.ne(StudyRole.APPLY));
    }

    private BooleanBuilder equalsMemberId(Long memberId) {
        return nullSafeBuilder(() -> studyJoin.member.id.eq(memberId));
    }

    private BooleanBuilder equalsStudyJoinId(Long studyJoinId) {
        return nullSafeBuilder(() -> studyJoin.id.eq(studyJoinId));
    }

    private BooleanBuilder isStudyCreatorOrAdmin() {
        return isStudyCreator().or(isStudyAdmin());
    }

    private BooleanBuilder isStudyCreator() {
        return nullSafeBuilder(() -> studyJoin.studyRole.eq(StudyRole.CREATOR));
    }
    
    private BooleanBuilder isStudyAdmin() {
        return nullSafeBuilder(() -> studyJoin.studyRole.eq(StudyRole.ADMIN));
    }
}
