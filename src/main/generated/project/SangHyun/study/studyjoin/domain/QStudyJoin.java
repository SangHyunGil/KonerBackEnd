package project.SangHyun.study.studyjoin.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyJoin is a Querydsl query type for StudyJoin
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyJoin extends EntityPathBase<StudyJoin> {

    private static final long serialVersionUID = -1484349391L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyJoin studyJoin = new QStudyJoin("studyJoin");

    public final project.SangHyun.common.QEntityDate _super = new project.SangHyun.common.QEntityDate(this);

    public final QApplyContent applyContent;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final project.SangHyun.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final project.SangHyun.study.study.domain.QStudy study;

    public final EnumPath<project.SangHyun.study.study.domain.StudyRole> studyRole = createEnum("studyRole", project.SangHyun.study.study.domain.StudyRole.class);

    public QStudyJoin(String variable) {
        this(StudyJoin.class, forVariable(variable), INITS);
    }

    public QStudyJoin(Path<? extends StudyJoin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyJoin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyJoin(PathMetadata metadata, PathInits inits) {
        this(StudyJoin.class, metadata, inits);
    }

    public QStudyJoin(Class<? extends StudyJoin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.applyContent = inits.isInitialized("applyContent") ? new QApplyContent(forProperty("applyContent")) : null;
        this.member = inits.isInitialized("member") ? new project.SangHyun.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.study = inits.isInitialized("study") ? new project.SangHyun.study.study.domain.QStudy(forProperty("study"), inits.get("study")) : null;
    }

}

