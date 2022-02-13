package project.SangHyun.study.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidHeadCountException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeadCount {

    private static final int MIN_COUNT = 1;
    private static final int MAX_COUNT = 99;

    @Column(nullable = false)
    private Long headCount;

    public HeadCount(Long headCount) {
        if (isNotValidHeadCount(headCount)) {
            throw new InvalidHeadCountException();
        }
        this.headCount = headCount;
    }

    private boolean isNotValidHeadCount(Long headCount) {
        return Objects.isNull(headCount) ||
                headCount < MIN_COUNT || headCount > MAX_COUNT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeadCount)) return false;
        HeadCount headCount1 = (HeadCount) o;
        return getHeadCount().equals(headCount1.getHeadCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeadCount());
    }
}
