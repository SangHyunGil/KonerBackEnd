package project.SangHyun.study.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidStudyDescriptionException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Description {

    private static final int MAX_LENGTH = 1000;

    @Column(nullable = false, length = MAX_LENGTH)
    private String description;

    public Description(String description) {
        if (isNotValidDescription(description)) {
            throw new InvalidStudyDescriptionException();
        }
        this.description = description;
    }

    private boolean isNotValidDescription(String description) {
        return Objects.isNull(description) || description.isBlank() ||
                description.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description)) return false;
        Description that = (Description) o;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }
}
