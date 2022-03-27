package project.SangHyun.study.videoroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVideoRoomTitle is a Querydsl query type for VideoRoomTitle
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QVideoRoomTitle extends BeanPath<VideoRoomTitle> {

    private static final long serialVersionUID = 542896711L;

    public static final QVideoRoomTitle videoRoomTitle = new QVideoRoomTitle("videoRoomTitle");

    public final StringPath title = createString("title");

    public QVideoRoomTitle(String variable) {
        super(VideoRoomTitle.class, forVariable(variable));
    }

    public QVideoRoomTitle(Path<? extends VideoRoomTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVideoRoomTitle(PathMetadata metadata) {
        super(VideoRoomTitle.class, metadata);
    }

}

