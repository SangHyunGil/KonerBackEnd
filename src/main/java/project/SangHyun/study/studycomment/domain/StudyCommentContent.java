package project.SangHyun.study.studycomment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidStudyCommentContentException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyCommentContent {

    private static final int MAX_LENGTH = 100;

    @Column(nullable = false, length = MAX_LENGTH)
    private String content;

    public StudyCommentContent(String content) {
        if (isValidStudyCommentContent(content)) {
            throw new InvalidStudyCommentContentException();
        }
        this.content = content;
    }

    private boolean isValidStudyCommentContent(String content) {
        return Objects.isNull(content) || content.isBlank() ||
                content.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyCommentContent)) return false;
        StudyCommentContent that = (StudyCommentContent) o;
        return getContent().equals(that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}
