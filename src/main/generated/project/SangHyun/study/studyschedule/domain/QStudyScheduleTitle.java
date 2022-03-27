package project.SangHyun.study.studyschedule.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyScheduleTitle is a Querydsl query type for StudyScheduleTitle
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyScheduleTitle extends BeanPath<StudyScheduleTitle> {

    private static final long serialVersionUID = -1622383609L;

    public static final QStudyScheduleTitle studyScheduleTitle = new QStudyScheduleTitle("studyScheduleTitle");

    public final StringPath title = createString("title");

    public QStudyScheduleTitle(String variable) {
        super(StudyScheduleTitle.class, forVariable(variable));
    }

    public QStudyScheduleTitle(Path<? extends StudyScheduleTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyScheduleTitle(PathMetadata metadata) {
        super(StudyScheduleTitle.class, metadata);
    }

}

