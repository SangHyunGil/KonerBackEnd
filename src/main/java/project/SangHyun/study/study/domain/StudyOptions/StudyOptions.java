package project.SangHyun.study.study.domain.StudyOptions;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class StudyOptions {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyState studyState;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitState recruitState;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyMethod studyMethod;
}
