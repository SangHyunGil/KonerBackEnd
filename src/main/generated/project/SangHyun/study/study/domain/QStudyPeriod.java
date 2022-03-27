package project.SangHyun.study.study.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyPeriod is a Querydsl query type for StudyPeriod
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyPeriod extends BeanPath<StudyPeriod> {

    private static final long serialVersionUID = 1221957234L;

    public static final QStudyPeriod studyPeriod = new QStudyPeriod("studyPeriod");

    public final StringPath endDate = createString("endDate");

    public final StringPath startDate = createString("startDate");

    public QStudyPeriod(String variable) {
        super(StudyPeriod.class, forVariable(variable));
    }

    public QStudyPeriod(Path<? extends StudyPeriod> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyPeriod(PathMetadata metadata) {
        super(StudyPeriod.class, metadata);
    }

}

