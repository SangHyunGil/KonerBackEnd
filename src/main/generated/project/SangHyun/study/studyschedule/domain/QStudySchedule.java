package project.SangHyun.study.studyschedule.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudySchedule is a Querydsl query type for StudySchedule
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudySchedule extends EntityPathBase<StudySchedule> {

    private static final long serialVersionUID = -476927791L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudySchedule studySchedule = new QStudySchedule("studySchedule");

    public final project.SangHyun.common.QEntityDate _super = new project.SangHyun.common.QEntityDate(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QSchedulePeriod period;

    public final project.SangHyun.study.study.domain.QStudy study;

    public final QStudyScheduleTitle title;

    public QStudySchedule(String variable) {
        this(StudySchedule.class, forVariable(variable), INITS);
    }

    public QStudySchedule(Path<? extends StudySchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudySchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudySchedule(PathMetadata metadata, PathInits inits) {
        this(StudySchedule.class, metadata, inits);
    }

    public QStudySchedule(Class<? extends StudySchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.period = inits.isInitialized("period") ? new QSchedulePeriod(forProperty("period")) : null;
        this.study = inits.isInitialized("study") ? new project.SangHyun.study.study.domain.QStudy(forProperty("study"), inits.get("study")) : null;
        this.title = inits.isInitialized("title") ? new QStudyScheduleTitle(forProperty("title")) : null;
    }

}

