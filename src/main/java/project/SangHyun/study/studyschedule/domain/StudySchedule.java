package project.SangHyun.study.studyschedule.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.SangHyun.study.study.domain.Study;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private StudyScheduleTitle title;

    @Embedded
    private SchedulePeriod period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Study study;

    @Builder
    public StudySchedule(String title, String startDate, String endDate, String startTime, String endTime, Study study) {
        this.title = new StudyScheduleTitle(title);
        this.period = new SchedulePeriod(startDate, endDate, startTime, endTime);
        this.study = study;
    }

    public void update(StudySchedule studySchedule) {
        this.title = studySchedule.getTitle();
        this.period = studySchedule.getPeriod();
    }

    public String getTitleName() {
        return title.getTitle();
    }
}
