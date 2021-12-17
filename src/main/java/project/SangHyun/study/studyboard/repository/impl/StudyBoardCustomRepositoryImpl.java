package project.SangHyun.study.studyboard.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.repository.StudyBoardCustomRepository;

import java.util.List;

import static project.SangHyun.domain.entity.QStudyBoard.studyBoard;

@RequiredArgsConstructor
public class StudyBoardCustomRepositoryImpl implements StudyBoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StudyBoard> findBoards(Long studyId) {
        return jpaQueryFactory.selectFrom(studyBoard)
                .where(studyBoard.study.id.eq(studyId))
                .fetch();
    }
}
