package project.SangHyun.study.studyarticle.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidStudyArticleTitleException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyArticleTitle {

    private static final int MAX_LENGTH = 30;

    @Column(nullable = false, length = MAX_LENGTH)
    private String title;

    public StudyArticleTitle(String title) {
        if (isNotValidStudyArticleTitle(title)) {
            throw new InvalidStudyArticleTitleException();
        }
        this.title = title;
    }

    private boolean isNotValidStudyArticleTitle(String title) {
        return Objects.isNull(title) || title.isBlank() ||
                title.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyArticleTitle)) return false;
        StudyArticleTitle that = (StudyArticleTitle) o;
        return getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
