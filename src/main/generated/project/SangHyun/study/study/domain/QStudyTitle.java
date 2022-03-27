package project.SangHyun.study.study.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyTitle is a Querydsl query type for StudyTitle
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyTitle extends BeanPath<StudyTitle> {

    private static final long serialVersionUID = 2121443207L;

    public static final QStudyTitle studyTitle = new QStudyTitle("studyTitle");

    public final StringPath title = createString("title");

    public QStudyTitle(String variable) {
        super(StudyTitle.class, forVariable(variable));
    }

    public QStudyTitle(Path<? extends StudyTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyTitle(PathMetadata metadata) {
        super(StudyTitle.class, metadata);
    }

}

