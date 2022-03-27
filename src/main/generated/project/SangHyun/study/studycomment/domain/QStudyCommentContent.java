package project.SangHyun.study.studycomment.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyCommentContent is a Querydsl query type for StudyCommentContent
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyCommentContent extends BeanPath<StudyCommentContent> {

    private static final long serialVersionUID = -1403739068L;

    public static final QStudyCommentContent studyCommentContent = new QStudyCommentContent("studyCommentContent");

    public final StringPath content = createString("content");

    public QStudyCommentContent(String variable) {
        super(StudyCommentContent.class, forVariable(variable));
    }

    public QStudyCommentContent(Path<? extends StudyCommentContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyCommentContent(PathMetadata metadata) {
        super(StudyCommentContent.class, metadata);
    }

}

