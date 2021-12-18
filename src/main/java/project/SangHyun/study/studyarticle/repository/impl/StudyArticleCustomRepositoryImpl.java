package project.SangHyun.study.studyarticle.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleCustomRepository;
import java.util.List;

import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.study.studyarticle.domain.QStudyArticle.studyArticle;
import static project.SangHyun.study.studyboard.domain.QStudyBoard.studyBoard;

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
