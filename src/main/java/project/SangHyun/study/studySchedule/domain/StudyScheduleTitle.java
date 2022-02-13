package project.SangHyun.study.studySchedule.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidStudyScheduleTitle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyScheduleTitle {

    private static final int MAX_LENGTH = 30;

    @Column(nullable = false, length = MAX_LENGTH)
    private String title;

    public StudyScheduleTitle(String title) {
        if (isNotValidStudyScheduleTitle(title)) {
            throw new InvalidStudyScheduleTitle();
        }
        this.title = title;
    }

    private boolean isNotValidStudyScheduleTitle(String title) {
        return Objects.isNull(title) || title.isBlank() ||
                title.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyScheduleTitle)) return false;
        StudyScheduleTitle that = (StudyScheduleTitle) o;
        return getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
