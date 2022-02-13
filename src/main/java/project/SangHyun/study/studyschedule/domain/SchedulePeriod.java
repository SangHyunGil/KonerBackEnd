package project.SangHyun.study.studyschedule.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchedulePeriod {

    @Column(nullable = false)
    private String startDate;
    @Column(nullable = false)
    private String endDate;

    @Column(nullable = false)
    private String startTime;
    @Column(nullable = false)
    private String endTime;

    public SchedulePeriod(String startDate, String endDate, String startTime, String endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
