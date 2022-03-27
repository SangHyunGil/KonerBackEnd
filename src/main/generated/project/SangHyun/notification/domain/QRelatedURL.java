package project.SangHyun.notification.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRelatedURL is a Querydsl query type for RelatedURL
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRelatedURL extends BeanPath<RelatedURL> {

    private static final long serialVersionUID = 1504556435L;

    public static final QRelatedURL relatedURL = new QRelatedURL("relatedURL");

    public final StringPath url = createString("url");

    public QRelatedURL(String variable) {
        super(RelatedURL.class, forVariable(variable));
    }

    public QRelatedURL(Path<? extends RelatedURL> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRelatedURL(PathMetadata metadata) {
        super(RelatedURL.class, metadata);
    }

}

