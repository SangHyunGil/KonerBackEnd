package project.SangHyun.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberProfileImgUrl is a Querydsl query type for MemberProfileImgUrl
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QMemberProfileImgUrl extends BeanPath<MemberProfileImgUrl> {

    private static final long serialVersionUID = -1512330051L;

    public static final QMemberProfileImgUrl memberProfileImgUrl = new QMemberProfileImgUrl("memberProfileImgUrl");

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public QMemberProfileImgUrl(String variable) {
        super(MemberProfileImgUrl.class, forVariable(variable));
    }

    public QMemberProfileImgUrl(Path<? extends MemberProfileImgUrl> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberProfileImgUrl(PathMetadata metadata) {
        super(MemberProfileImgUrl.class, metadata);
    }

}

