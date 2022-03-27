package project.SangHyun.study.studyarticle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyArticleContent is a Querydsl query type for StudyArticleContent
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyArticleContent extends BeanPath<StudyArticleContent> {

    private static final long serialVersionUID = 1675311318L;

    public static final QStudyArticleContent studyArticleContent = new QStudyArticleContent("studyArticleContent");

    public final StringPath content = createString("content");

    public QStudyArticleContent(String variable) {
        super(StudyArticleContent.class, forVariable(variable));
    }

    public QStudyArticleContent(Path<? extends StudyArticleContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyArticleContent(PathMetadata metadata) {
        super(StudyArticleContent.class, metadata);
    }

}

