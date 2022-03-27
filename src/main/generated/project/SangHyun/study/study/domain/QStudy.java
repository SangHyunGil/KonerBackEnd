package project.SangHyun.study.study.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudy is a Querydsl query type for Study
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudy extends EntityPathBase<Study> {

    private static final long serialVersionUID = -1485818543L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudy study = new QStudy("study");

    public final project.SangHyun.common.QEntityDate _super = new project.SangHyun.common.QEntityDate(this);

    public final EnumPath<StudyCategory> category = createEnum("category", StudyCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QDescription description;

    public final QHeadCount headCount;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final project.SangHyun.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QStudyProfileImgUrl profileImgUrl;

    public final ListPath<project.SangHyun.study.studyboard.domain.StudyBoard, project.SangHyun.study.studyboard.domain.QStudyBoard> studyBoards = this.<project.SangHyun.study.studyboard.domain.StudyBoard, project.SangHyun.study.studyboard.domain.QStudyBoard>createList("studyBoards", project.SangHyun.study.studyboard.domain.StudyBoard.class, project.SangHyun.study.studyboard.domain.QStudyBoard.class, PathInits.DIRECT2);

    public final ListPath<project.SangHyun.study.studyjoin.domain.StudyJoin, project.SangHyun.study.studyjoin.domain.QStudyJoin> studyJoins = this.<project.SangHyun.study.studyjoin.domain.StudyJoin, project.SangHyun.study.studyjoin.domain.QStudyJoin>createList("studyJoins", project.SangHyun.study.studyjoin.domain.StudyJoin.class, project.SangHyun.study.studyjoin.domain.QStudyJoin.class, PathInits.DIRECT2);

    public final project.SangHyun.study.study.domain.StudyOptions.QStudyOptions studyOptions;

    public final QStudyPeriod studyPeriod;

    public final project.SangHyun.study.study.domain.Tag.QTags tags;

    public final QStudyTitle title;

    public QStudy(String variable) {
        this(Study.class, forVariable(variable), INITS);
    }

    public QStudy(Path<? extends Study> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudy(PathMetadata metadata, PathInits inits) {
        this(Study.class, metadata, inits);
    }

    public QStudy(Class<? extends Study> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.description = inits.isInitialized("description") ? new QDescription(forProperty("description")) : null;
        this.headCount = inits.isInitialized("headCount") ? new QHeadCount(forProperty("headCount")) : null;
        this.member = inits.isInitialized("member") ? new project.SangHyun.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.profileImgUrl = inits.isInitialized("profileImgUrl") ? new QStudyProfileImgUrl(forProperty("profileImgUrl")) : null;
        this.studyOptions = inits.isInitialized("studyOptions") ? new project.SangHyun.study.study.domain.StudyOptions.QStudyOptions(forProperty("studyOptions")) : null;
        this.studyPeriod = inits.isInitialized("studyPeriod") ? new QStudyPeriod(forProperty("studyPeriod")) : null;
        this.tags = inits.isInitialized("tags") ? new project.SangHyun.study.study.domain.Tag.QTags(forProperty("tags")) : null;
        this.title = inits.isInitialized("title") ? new QStudyTitle(forProperty("title")) : null;
    }

}

