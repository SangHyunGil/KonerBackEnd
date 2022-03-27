package project.SangHyun.study.studyarticle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyArticleTitle is a Querydsl query type for StudyArticleTitle
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyArticleTitle extends BeanPath<StudyArticleTitle> {

    private static final long serialVersionUID = 750230005L;

    public static final QStudyArticleTitle studyArticleTitle = new QStudyArticleTitle("studyArticleTitle");

    public final StringPath title = createString("title");

    public QStudyArticleTitle(String variable) {
        super(StudyArticleTitle.class, forVariable(variable));
    }

    public QStudyArticleTitle(Path<? extends StudyArticleTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyArticleTitle(PathMetadata metadata) {
        super(StudyArticleTitle.class, metadata);
    }

}

