package project.SangHyun.study.studyboard.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyBoardTitle is a Querydsl query type for StudyBoardTitle
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyBoardTitle extends BeanPath<StudyBoardTitle> {

    private static final long serialVersionUID = -1633688107L;

    public static final QStudyBoardTitle studyBoardTitle = new QStudyBoardTitle("studyBoardTitle");

    public final StringPath title = createString("title");

    public QStudyBoardTitle(String variable) {
        super(StudyBoardTitle.class, forVariable(variable));
    }

    public QStudyBoardTitle(Path<? extends StudyBoardTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyBoardTitle(PathMetadata metadata) {
        super(StudyBoardTitle.class, metadata);
    }

}

