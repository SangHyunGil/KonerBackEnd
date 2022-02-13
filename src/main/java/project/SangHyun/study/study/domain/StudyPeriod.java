package project.SangHyun.study.study.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class StudyPeriod {
    @Column(nullable = false)
    private String startDate;
    @Column(nullable = false)
    private String endDate;
}
