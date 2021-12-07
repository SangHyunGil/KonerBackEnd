package project.SangHyun.domain.repository.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.SangHyun.domain.entity.QMember;
import project.SangHyun.domain.entity.QStudy;
import project.SangHyun.domain.entity.QStudyBoardCategory;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.repository.StudyCustomRepository;
import project.SangHyun.dto.response.StudyFindResponseDto;

import java.util.List;

import static project.SangHyun.domain.entity.QMember.member;
import static project.SangHyun.domain.entity.QStudy.study;
import static project.SangHyun.domain.entity.QStudyBoardCategory.studyBoardCategory;
import static project.SangHyun.domain.entity.QStudyJoin.studyJoin;

@Slf4j
@RequiredArgsConstructor
public class StudyCustomRepositoryImpl implements StudyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Study findStudyById(Long studyId) {
        return jpaQueryFactory.select(study)
                .from(study)
                .innerJoin(study.studyBoardCategories, studyBoardCategory)
                .innerJoin(study.member, member).fetchJoin()
                .innerJoin(study.studyJoins, studyJoin).fetchJoin()
                .innerJoin(studyJoin.member, member).fetchJoin()
                .where(study.id.eq(studyId))
                .distinct()
                .fetchOne();
    }
}
