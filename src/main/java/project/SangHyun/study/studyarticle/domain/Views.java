package project.SangHyun.study.studyarticle.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidViewsException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Views {
    private static final int MIN_COUNT = 0;

    @Column(nullable = false)
    private Long views;

    public Views(Long views) {
        if (isNotValidViews(views)) {
            throw new InvalidViewsException();
        }
        this.views = views;
    }

    private boolean isNotValidViews(Long views) {
        return Objects.isNull(views) || views < MIN_COUNT;
    }

    public void increase() {
        this.views += 1;
    }
}
