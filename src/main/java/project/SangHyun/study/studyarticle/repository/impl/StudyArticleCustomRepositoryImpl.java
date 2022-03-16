package project.SangHyun.study.studyarticle.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleCustomRepository;

import java.util.List;

import static project.SangHyun.helper.BooleanBuilderHelper.nullSafeBuilder;
import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.study.studyarticle.domain.QStudyArticle.studyArticle;

@RequiredArgsConstructor
public class StudyArticleCustomRepositoryImpl implements StudyArticleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<StudyArticle> findAllOrderByStudyArticleIdDesc(Long boardId, Pageable pageable) {
        List<StudyArticle> studyArticles = jpaQueryFactory
                .selectFrom(studyArticle)
                .innerJoin(studyArticle.member, member).fetchJoin()
                .where(equalsBoardId(boardId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<StudyArticle> countQuery = jpaQueryFactory
                .selectFrom(studyArticle)
                .where(equalsBoardId(boardId));

        return PageableExecutionUtils.getPage(studyArticles, pageable, countQuery::fetchCount);
    }

    private BooleanBuilder equalsBoardId(Long boardId) {
        return nullSafeBuilder(() -> studyArticle.studyBoard.id.eq(boardId));
    }

    @Override
    public List<StudyArticle> findArticleByTitle(String title) {
        return jpaQueryFactory
                .selectFrom(studyArticle)
                .where(equalsStudyTitle(title))
                .fetch();
    }

    private BooleanBuilder equalsStudyTitle(String title) {
        return nullSafeBuilder(() -> studyArticle.title.title.contains(title));
    }
}
