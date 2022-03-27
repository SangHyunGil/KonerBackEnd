package project.SangHyun.message.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMessageContent is a Querydsl query type for MessageContent
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QMessageContent extends BeanPath<MessageContent> {

    private static final long serialVersionUID = -1988044211L;

    public static final QMessageContent messageContent = new QMessageContent("messageContent");

    public final StringPath content = createString("content");

    public QMessageContent(String variable) {
        super(MessageContent.class, forVariable(variable));
    }

    public QMessageContent(Path<? extends MessageContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessageContent(PathMetadata metadata) {
        super(MessageContent.class, metadata);
    }

}

