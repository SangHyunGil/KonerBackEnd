package project.SangHyun.study.study.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDescription is a Querydsl query type for Description
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QDescription extends BeanPath<Description> {

    private static final long serialVersionUID = 1739646340L;

    public static final QDescription description1 = new QDescription("description1");

    public final StringPath description = createString("description");

    public QDescription(String variable) {
        super(Description.class, forVariable(variable));
    }

    public QDescription(Path<? extends Description> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDescription(PathMetadata metadata) {
        super(Description.class, metadata);
    }

}

