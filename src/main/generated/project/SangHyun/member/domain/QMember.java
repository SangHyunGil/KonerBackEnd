package project.SangHyun.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1483788248L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final project.SangHyun.common.QEntityDate _super = new project.SangHyun.common.QEntityDate(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<Department> department = createEnum("department", Department.class);

    public final QEmail email;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QIntroduction introduction;

    public final EnumPath<MemberRole> memberRole = createEnum("memberRole", MemberRole.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QNickname nickname;

    public final QPassword password;

    public final QMemberProfileImgUrl profileImgUrl;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.email = inits.isInitialized("email") ? new QEmail(forProperty("email")) : null;
        this.introduction = inits.isInitialized("introduction") ? new QIntroduction(forProperty("introduction")) : null;
        this.nickname = inits.isInitialized("nickname") ? new QNickname(forProperty("nickname")) : null;
        this.password = inits.isInitialized("password") ? new QPassword(forProperty("password")) : null;
        this.profileImgUrl = inits.isInitialized("profileImgUrl") ? new QMemberProfileImgUrl(forProperty("profileImgUrl")) : null;
    }

}

