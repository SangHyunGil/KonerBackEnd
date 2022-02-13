package project.SangHyun.study.studyboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidStudyBoardTitleException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyBoardTitle {

    private static final int MAX_LENGTH = 30;

    @Column(nullable = false, length = MAX_LENGTH)
    private String title;

    public StudyBoardTitle(String title) {
        if (isNotValidBoardTitle(title)) {
            throw new InvalidStudyBoardTitleException();
        }
        this.title = title;
    }

    private boolean isNotValidBoardTitle(String title) {
        return Objects.isNull(title) || title.isBlank() ||
                title.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyBoardTitle)) return false;
        StudyBoardTitle that = (StudyBoardTitle) o;
        return getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
