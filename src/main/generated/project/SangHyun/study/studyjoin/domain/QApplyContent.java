package project.SangHyun.study.studyjoin.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QApplyContent is a Querydsl query type for ApplyContent
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QApplyContent extends BeanPath<ApplyContent> {

    private static final long serialVersionUID = -1204906291L;

    public static final QApplyContent applyContent1 = new QApplyContent("applyContent1");

    public final StringPath applyContent = createString("applyContent");

    public QApplyContent(String variable) {
        super(ApplyContent.class, forVariable(variable));
    }

    public QApplyContent(Path<? extends ApplyContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QApplyContent(PathMetadata metadata) {
        super(ApplyContent.class, metadata);
    }

}

