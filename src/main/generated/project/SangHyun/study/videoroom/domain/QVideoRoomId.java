package project.SangHyun.study.videoroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVideoRoomId is a Querydsl query type for VideoRoomId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QVideoRoomId extends BeanPath<VideoRoomId> {

    private static final long serialVersionUID = 1242618764L;

    public static final QVideoRoomId videoRoomId = new QVideoRoomId("videoRoomId");

    public final NumberPath<Long> roomId = createNumber("roomId", Long.class);

    public QVideoRoomId(String variable) {
        super(VideoRoomId.class, forVariable(variable));
    }

    public QVideoRoomId(Path<? extends VideoRoomId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVideoRoomId(PathMetadata metadata) {
        super(VideoRoomId.class, metadata);
    }

}

