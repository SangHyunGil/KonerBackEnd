package project.SangHyun.study.study.domain.StudyOptions;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyOptions is a Querydsl query type for StudyOptions
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QStudyOptions extends BeanPath<StudyOptions> {

    private static final long serialVersionUID = 1230138614L;

    public static final QStudyOptions studyOptions = new QStudyOptions("studyOptions");

    public final EnumPath<RecruitState> recruitState = createEnum("recruitState", RecruitState.class);

    public final EnumPath<StudyMethod> studyMethod = createEnum("studyMethod", StudyMethod.class);

    public final EnumPath<StudyState> studyState = createEnum("studyState", StudyState.class);

    public QStudyOptions(String variable) {
        super(StudyOptions.class, forVariable(variable));
    }

    public QStudyOptions(Path<? extends StudyOptions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyOptions(PathMetadata metadata) {
        super(StudyOptions.class, metadata);
    }

}

