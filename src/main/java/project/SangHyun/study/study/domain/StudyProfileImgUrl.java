package project.SangHyun.study.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyProfileImgUrl {

    private static final String STUDY_DEFAULT_IMG = "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/logo/KakaoTalk_20220128_143615435.png";

    @Column(nullable = false)
    private String profileImgUrl;

    public StudyProfileImgUrl(String profileImgUrl) {
        if (NotUploadedProfileImg(profileImgUrl)) {
            profileImgUrl = STUDY_DEFAULT_IMG;
        }
        this.profileImgUrl = profileImgUrl;
    }

    private boolean NotUploadedProfileImg(String profileImgUrl) {
        return Objects.isNull(profileImgUrl) || profileImgUrl.isBlank();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyProfileImgUrl)) return false;
        StudyProfileImgUrl that = (StudyProfileImgUrl) o;
        return getProfileImgUrl().equals(that.getProfileImgUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProfileImgUrl());
    }
}
