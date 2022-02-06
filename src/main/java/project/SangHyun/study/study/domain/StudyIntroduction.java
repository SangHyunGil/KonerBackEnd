package project.SangHyun.study.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidStudyIntroductionException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyIntroduction {

    private static final int MAX_LENGTH = 1000;

    @Column(nullable = false, length = MAX_LENGTH)
    private String introduction;

    public StudyIntroduction(String introduction) {
        if (isNotValidIntroduction(introduction)) {
            throw new InvalidStudyIntroductionException();
        }
        this.introduction = introduction;
    }

    private boolean isNotValidIntroduction(String introduction) {
        return Objects.isNull(introduction) || introduction.isBlank() ||
                introduction.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyIntroduction)) return false;
        StudyIntroduction that = (StudyIntroduction) o;
        return getIntroduction().equals(that.getIntroduction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIntroduction());
    }
}
