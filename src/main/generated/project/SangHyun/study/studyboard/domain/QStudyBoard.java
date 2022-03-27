package project.SangHyun.study.studyboard.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyBoard is a Querydsl query type for StudyBoard
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyBoard extends EntityPathBase<StudyBoard> {

    private static final long serialVersionUID = -1328759229L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyBoard studyBoard = new QStudyBoard("studyBoard");

    public final project.SangHyun.common.QEntityDate _super = new project.SangHyun.common.QEntityDate(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final project.SangHyun.study.study.domain.QStudy study;

    public final QStudyBoardTitle title;

    public QStudyBoard(String variable) {
        this(StudyBoard.class, forVariable(variable), INITS);
    }

    public QStudyBoard(Path<? extends StudyBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyBoard(PathMetadata metadata, PathInits inits) {
        this(StudyBoard.class, metadata, inits);
    }

    public QStudyBoard(Class<? extends StudyBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.study = inits.isInitialized("study") ? new project.SangHyun.study.study.domain.QStudy(forProperty("study"), inits.get("study")) : null;
        this.title = inits.isInitialized("title") ? new QStudyBoardTitle(forProperty("title")) : null;
    }

}

