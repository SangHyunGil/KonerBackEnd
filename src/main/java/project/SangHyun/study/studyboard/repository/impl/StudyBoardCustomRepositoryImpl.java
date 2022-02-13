package project.SangHyun.study.studyboard.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.repository.StudyBoardCustomRepository;
import java.util.List;

import static project.SangHyun.common.helper.BooleanBuilderHelper.nullSafeBuilder;
import static project.SangHyun.study.studyboard.domain.QStudyBoard.studyBoard;


@RequiredArgsConstructor
public class StudyBoardCustomRepositoryImpl implements StudyBoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StudyBoard> findBoards(Long studyId) {
        return jpaQueryFactory.selectFrom(studyBoard)
                .where(equalsStudyId(studyId))
                .fetch();
    }

    private BooleanBuilder equalsStudyId(Long studyId) {
        return nullSafeBuilder(() -> studyBoard.study.id.eq(studyId));
    }
}
