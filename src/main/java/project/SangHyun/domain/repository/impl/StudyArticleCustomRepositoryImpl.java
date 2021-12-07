package project.SangHyun.domain.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.domain.entity.StudyArticle;
import project.SangHyun.domain.repository.StudyArticleCustomRepository;

import java.util.List;

import static project.SangHyun.domain.entity.QMember.member;
import static project.SangHyun.domain.entity.QStudyArticle.studyArticle;
import static project.SangHyun.domain.entity.QStudyBoard.studyBoard;


@RequiredArgsConstructor
public class StudyArticleCustomRepositoryImpl implements StudyArticleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StudyArticle> findAllArticles(Long boardId) {
        return jpaQueryFactory
                .selectFrom(studyArticle)
                .innerJoin(studyArticle.member, member).fetchJoin()
                .innerJoin(studyArticle.studyBoard, studyBoard).fetchJoin()
                .where(studyArticle.studyBoard.id.eq(boardId))
                .fetch();
    }
}
