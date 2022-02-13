package project.SangHyun.study.studyarticle.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidStudyArticleContentException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyArticleContent {

    private static final int MAX_LENGTH = 1000;

    @Column(nullable = false, length = MAX_LENGTH)
    private String content;

    public StudyArticleContent(String content) {
        if (isNotValidStudyArticleContent(content)) {
            throw new InvalidStudyArticleContentException();
        }
        this.content = content;
    }

    private boolean isNotValidStudyArticleContent(String content) {
        return Objects.isNull(content) || content.isBlank() ||
                content.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyArticleContent)) return false;
        StudyArticleContent that = (StudyArticleContent) o;
        return getContent().equals(that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}
