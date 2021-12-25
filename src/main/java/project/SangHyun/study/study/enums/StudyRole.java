package project.SangHyun.study.study.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudyRole {
    APPLY("STUDY_APPLY"), MEMBER("STUDY_MEMBER"), CREATOR("STUDY_CREATOR"), ADMIN("STUDY_ADMIN");

    private String desc;
}
