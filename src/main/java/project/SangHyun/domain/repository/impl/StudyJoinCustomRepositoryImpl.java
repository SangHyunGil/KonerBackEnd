package project.SangHyun.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.domain.entity.Board;
import project.SangHyun.domain.entity.QBoard;
import project.SangHyun.domain.entity.QStudyJoin;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.StudyJoinCustomRepository;

import java.util.List;

import static project.SangHyun.domain.entity.QBoard.board;
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

    @Override
    public List<Board> findStudyByMemberId(Long memberId) {
        return jpaQueryFactory
                .select(board)
                .from(studyJoin)
                .where(studyJoin.member.id.eq(memberId))
                .fetch();
    }
}
