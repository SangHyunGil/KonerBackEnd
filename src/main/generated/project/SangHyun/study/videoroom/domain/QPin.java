package project.SangHyun.study.videoroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPin is a Querydsl query type for Pin
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPin extends BeanPath<Pin> {

    private static final long serialVersionUID = -102211472L;

    public static final QPin pin1 = new QPin("pin1");

    public final StringPath pin = createString("pin");

    public QPin(String variable) {
        super(Pin.class, forVariable(variable));
    }

    public QPin(Path<? extends Pin> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPin(PathMetadata metadata) {
        super(Pin.class, metadata);
    }

}

