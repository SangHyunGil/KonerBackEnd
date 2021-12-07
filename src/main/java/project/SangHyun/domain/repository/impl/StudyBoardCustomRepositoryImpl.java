package project.SangHyun.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.domain.entity.QStudyBoard;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.repository.StudyBoardCustomRepository;

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
