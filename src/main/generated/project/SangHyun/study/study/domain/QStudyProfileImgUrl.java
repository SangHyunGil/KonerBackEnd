package project.SangHyun.study.study.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyProfileImgUrl is a Querydsl query type for StudyProfileImgUrl
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyProfileImgUrl extends BeanPath<StudyProfileImgUrl> {

    private static final long serialVersionUID = 798798820L;

    public static final QStudyProfileImgUrl studyProfileImgUrl = new QStudyProfileImgUrl("studyProfileImgUrl");

    public final StringPath profileImgUrl = createString("profileImgUrl");

    public QStudyProfileImgUrl(String variable) {
        super(StudyProfileImgUrl.class, forVariable(variable));
    }

    public QStudyProfileImgUrl(Path<? extends StudyProfileImgUrl> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyProfileImgUrl(PathMetadata metadata) {
        super(StudyProfileImgUrl.class, metadata);
    }

}

