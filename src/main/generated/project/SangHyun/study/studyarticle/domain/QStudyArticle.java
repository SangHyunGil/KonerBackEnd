package project.SangHyun.study.studyarticle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyArticle is a Querydsl query type for StudyArticle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyArticle extends EntityPathBase<StudyArticle> {

    private static final long serialVersionUID = 1769797155L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyArticle studyArticle = new QStudyArticle("studyArticle");

    public final project.SangHyun.common.QEntityDate _super = new project.SangHyun.common.QEntityDate(this);

    public final QStudyArticleContent content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final project.SangHyun.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final project.SangHyun.study.studyboard.domain.QStudyBoard studyBoard;

    public final QStudyArticleTitle title;

    public final QViews views;

    public QStudyArticle(String variable) {
        this(StudyArticle.class, forVariable(variable), INITS);
    }

    public QStudyArticle(Path<? extends StudyArticle> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyArticle(PathMetadata metadata, PathInits inits) {
        this(StudyArticle.class, metadata, inits);
    }

    public QStudyArticle(Class<? extends StudyArticle> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new QStudyArticleContent(forProperty("content")) : null;
        this.member = inits.isInitialized("member") ? new project.SangHyun.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.studyBoard = inits.isInitialized("studyBoard") ? new project.SangHyun.study.studyboard.domain.QStudyBoard(forProperty("studyBoard"), inits.get("studyBoard")) : null;
        this.title = inits.isInitialized("title") ? new QStudyArticleTitle(forProperty("title")) : null;
        this.views = inits.isInitialized("views") ? new QViews(forProperty("views")) : null;
    }

}

