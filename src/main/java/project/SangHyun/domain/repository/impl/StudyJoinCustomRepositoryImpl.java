package project.SangHyun.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.domain.entity.QStudyJoin;
import project.SangHyun.domain.repository.StudyJoinCustomRepository;

import static project.SangHyun.domain.entity.QStudyJoin.studyJoin;

@RequiredArgsConstructor
public class StudyJoinCustomRepositoryImpl implements StudyJoinCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findStudyMemberNum(Long boardId) {
        return jpaQueryFactory
                .selectFrom(studyJoin)
                .where(studyJoin.board.id.eq(boardId))
                .fetchCount();
    }
}
