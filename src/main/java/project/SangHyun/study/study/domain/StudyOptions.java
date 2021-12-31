package project.SangHyun.study.study.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.enums.RecruitState;
import project.SangHyun.study.study.domain.enums.StudyMethod;
import project.SangHyun.study.study.domain.enums.StudyState;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class StudyOptions {
    @Enumerated(EnumType.STRING)
    private StudyState studyState;
    @Enumerated(EnumType.STRING)
    private RecruitState recruitState;
    @Enumerated(EnumType.STRING)
    private StudyMethod studyMethod;
}
