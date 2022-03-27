package project.SangHyun.study.studyarticle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QViews is a Querydsl query type for Views
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QViews extends BeanPath<Views> {

    private static final long serialVersionUID = 994486264L;

    public static final QViews views1 = new QViews("views1");

    public final NumberPath<Long> views = createNumber("views", Long.class);

    public QViews(String variable) {
        super(Views.class, forVariable(variable));
    }

    public QViews(Path<? extends Views> path) {
        super(path.getType(), path.getMetadata());
    }

    public QViews(PathMetadata metadata) {
        super(Views.class, metadata);
    }

}

