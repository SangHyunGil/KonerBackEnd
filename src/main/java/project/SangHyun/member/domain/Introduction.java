package project.SangHyun.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidIntroductionException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Introduction {

    private static final String NO_INTRODUCTION = "작성된 소개글이 존재하지 않습니다.";
    private static final int MAX_LENGTH = 1000;

    @Column(nullable = false)
    private String introduction;

    public Introduction(String introduction) {
        if (hasNoIntroduction(introduction)) {
            introduction = NO_INTRODUCTION;
        }

        if (isNotValidIntroduction(introduction)) {
            throw new InvalidIntroductionException();
        }
        this.introduction = introduction;
    }

    private boolean hasNoIntroduction(String introduction) {
        return Objects.isNull(introduction) || introduction.isBlank();
    }

    private boolean isNotValidIntroduction(String introduction) {
        return introduction.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Introduction)) return false;
        Introduction that = (Introduction) o;
        return getIntroduction().equals(that.getIntroduction());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIntroduction());
    }
}
