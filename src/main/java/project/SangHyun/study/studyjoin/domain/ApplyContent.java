package project.SangHyun.study.studyjoin.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidApplyContentContent;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyContent {

    private static final int MAX_LENGTH = 1000;
    private static final String CREATOR_APPLY_CONTENT = "스터디 창설자입니다. 지원서 작성 내역이 존재하지 않습니다.";

    @Column(nullable = false, length = 1000)
    private String applyContent;

    public ApplyContent(String applyContent) {
        if (isStudyCreator(applyContent)) {
            applyContent = CREATOR_APPLY_CONTENT;
        }
        if (isNotValidApplyContent(applyContent)) {
            throw new InvalidApplyContentContent();
        }
        this.applyContent = applyContent;
    }

    private boolean isNotValidApplyContent(String applyContent) {
        return applyContent.length() > MAX_LENGTH;
    }

    private boolean isStudyCreator(String applyContent) {
        return applyContent == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplyContent)) return false;
        ApplyContent that = (ApplyContent) o;
        return getApplyContent().equals(that.getApplyContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApplyContent());
    }
}
