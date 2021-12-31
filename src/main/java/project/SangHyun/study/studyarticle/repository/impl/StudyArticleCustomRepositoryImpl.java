package project.SangHyun.study.studyarticle.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
    public Page<StudyArticle> findAllOrderByStudyArticleIdDesc(Long boardId, Pageable pageable) {
        List<StudyArticle> studyArticles = jpaQueryFactory
                .selectFrom(studyArticle)
                .innerJoin(studyArticle.member, member).fetchJoin()
                .where(studyArticle.studyBoard.id.eq(boardId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<StudyArticle> countQuery = jpaQueryFactory
                .selectFrom(studyArticle)
                .where(studyArticle.studyBoard.id.eq(boardId));

        return PageableExecutionUtils.getPage(studyArticles, pageable, countQuery::fetchCount);
    }

    @Override
    public List<StudyArticle> findArticleByTitle(String title) {
        return jpaQueryFactory
                .selectFrom(studyArticle)
                .where(studyArticle.title.contains(title))
                .fetch();
    }
}
