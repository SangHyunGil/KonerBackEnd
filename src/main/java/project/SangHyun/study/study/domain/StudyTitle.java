package project.SangHyun.study.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidStudyTitleException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyTitle {

    private static final int MAX_LENGTH = 30;

    @Column(nullable = false, length = MAX_LENGTH)
    private String title;

    public StudyTitle(String title) {
        if (isNotValidStudyTitle(title)) {
            throw new InvalidStudyTitleException();
        }
        this.title = title;
    }

    private boolean isNotValidStudyTitle(String title) {
        return Objects.isNull(title) || title.isBlank() ||
                title.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyTitle)) return false;
        StudyTitle that = (StudyTitle) o;
        return getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
