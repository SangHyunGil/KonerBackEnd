package project.SangHyun.study.studyschedule.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSchedulePeriod is a Querydsl query type for SchedulePeriod
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSchedulePeriod extends BeanPath<SchedulePeriod> {

    private static final long serialVersionUID = 1953338375L;

    public static final QSchedulePeriod schedulePeriod = new QSchedulePeriod("schedulePeriod");

    public final StringPath endDate = createString("endDate");

    public final StringPath endTime = createString("endTime");

    public final StringPath startDate = createString("startDate");

    public final StringPath startTime = createString("startTime");

    public QSchedulePeriod(String variable) {
        super(SchedulePeriod.class, forVariable(variable));
    }

    public QSchedulePeriod(Path<? extends SchedulePeriod> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchedulePeriod(PathMetadata metadata) {
        super(SchedulePeriod.class, metadata);
    }

}

