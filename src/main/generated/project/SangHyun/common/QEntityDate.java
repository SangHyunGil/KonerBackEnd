package project.SangHyun.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEntityDate is a Querydsl query type for EntityDate
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QEntityDate extends EntityPathBase<EntityDate> {

    private static final long serialVersionUID = 1471184428L;

    public static final QEntityDate entityDate = new QEntityDate("entityDate");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public QEntityDate(String variable) {
        super(EntityDate.class, forVariable(variable));
    }

    public QEntityDate(Path<? extends EntityDate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEntityDate(PathMetadata metadata) {
        super(EntityDate.class, metadata);
    }

}

