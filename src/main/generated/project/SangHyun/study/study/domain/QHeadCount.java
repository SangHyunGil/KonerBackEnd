package project.SangHyun.study.study.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QHeadCount is a Querydsl query type for HeadCount
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QHeadCount extends BeanPath<HeadCount> {

    private static final long serialVersionUID = -991993193L;

    public static final QHeadCount headCount1 = new QHeadCount("headCount1");

    public final NumberPath<Long> headCount = createNumber("headCount", Long.class);

    public QHeadCount(String variable) {
        super(HeadCount.class, forVariable(variable));
    }

    public QHeadCount(Path<? extends HeadCount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHeadCount(PathMetadata metadata) {
        super(HeadCount.class, metadata);
    }

}

