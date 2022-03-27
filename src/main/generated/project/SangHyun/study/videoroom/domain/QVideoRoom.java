package project.SangHyun.study.videoroom.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideoRoom is a Querydsl query type for VideoRoom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVideoRoom extends EntityPathBase<VideoRoom> {

    private static final long serialVersionUID = -2112671087L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideoRoom videoRoom = new QVideoRoom("videoRoom");

    public final project.SangHyun.common.QEntityDate _super = new project.SangHyun.common.QEntityDate(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final project.SangHyun.member.domain.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QPin pin;

    public final QVideoRoomId roomId;

    public final project.SangHyun.study.study.domain.QStudy study;

    public final QVideoRoomTitle title;

    public QVideoRoom(String variable) {
        this(VideoRoom.class, forVariable(variable), INITS);
    }

    public QVideoRoom(Path<? extends VideoRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideoRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideoRoom(PathMetadata metadata, PathInits inits) {
        this(VideoRoom.class, metadata, inits);
    }

    public QVideoRoom(Class<? extends VideoRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new project.SangHyun.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
        this.pin = inits.isInitialized("pin") ? new QPin(forProperty("pin")) : null;
        this.roomId = inits.isInitialized("roomId") ? new QVideoRoomId(forProperty("roomId")) : null;
        this.study = inits.isInitialized("study") ? new project.SangHyun.study.study.domain.QStudy(forProperty("study"), inits.get("study")) : null;
        this.title = inits.isInitialized("title") ? new QVideoRoomTitle(forProperty("title")) : null;
    }

}

