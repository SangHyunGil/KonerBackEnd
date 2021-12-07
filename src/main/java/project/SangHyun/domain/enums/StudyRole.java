package project.SangHyun.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudyRole {
    MEMBER("STUDY_MEMBER"), ADMIN("STUDY_ADMIN");

    private String desc;
}
