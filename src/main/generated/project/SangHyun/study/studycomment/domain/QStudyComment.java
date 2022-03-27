package project.SangHyun.study.studycomment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyComment is a Querydsl query type for StudyComment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyComment extends EntityPathBase<StudyComment> {

    private static final long serialVersionUID = -335975819L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyComment studyComment = new QStudyComment("studyComment");

    public final project.SangHyun.common.QEntityDate _super = new project.SangHyun.common.QEntityDate(this);

    public final ListPath<StudyComment, QStudyComment> children = this.<StudyComment, QStudyComment>createList("children", StudyComment.class, QStudyComment.class, PathInits.DIRECT2);

    public final QStudyCommentContent content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final project.SangHyun.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QStudyComment parent;

    public final project.SangHyun.study.studyarticle.domain.QStudyArticle studyArticle;

    public QStudyComment(String variable) {
        this(StudyComment.class, forVariable(variable), INITS);
    }

    public QStudyComment(Path<? extends StudyComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyComment(PathMetadata metadata, PathInits inits) {
        this(StudyComment.class, metadata, inits);
    }

    public QStudyComment(Class<? extends StudyComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new QStudyCommentContent(forProperty("content")) : null;
        this.member = inits.isInitialized("member") ? new project.SangHyun.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.parent = inits.isInitialized("parent") ? new QStudyComment(forProperty("parent"), inits.get("parent")) : null;
        this.studyArticle = inits.isInitialized("studyArticle") ? new project.SangHyun.study.studyarticle.domain.QStudyArticle(forProperty("studyArticle"), inits.get("studyArticle")) : null;
    }

}

