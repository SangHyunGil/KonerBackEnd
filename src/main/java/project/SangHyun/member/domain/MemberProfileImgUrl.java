package project.SangHyun.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.Random;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfileImgUrl {
    private static final String MEMBER_DEFAULT_IMG_PREFIX = "https://koner-bucket.s3.ap-northeast-2.amazonaws.com/profileImg/koryong";
    private static final String MEMBER_DEFAULT_IMG_POSTFIX = ".jpg";
    private static final int RANDOM_IMG_NUM_COUNT = 8;

    @Column(nullable = false)
    private String profileImgUrl;

    public MemberProfileImgUrl(String profileImgUrl) {
        if (NotUploadedProfileImg(profileImgUrl)) {
            profileImgUrl = getRandomProfileImgUrl();
        }
        this.profileImgUrl = profileImgUrl;
    }

    private boolean NotUploadedProfileImg(String profileImgUrl) {
        return Objects.isNull(profileImgUrl) || profileImgUrl.isBlank();
    }

    private String getRandomProfileImgUrl() {
        return MEMBER_DEFAULT_IMG_PREFIX + getRandomNumber() + MEMBER_DEFAULT_IMG_POSTFIX;
    }

    private int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(1 + RANDOM_IMG_NUM_COUNT) + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberProfileImgUrl)) return false;
        MemberProfileImgUrl that = (MemberProfileImgUrl) o;
        return getProfileImgUrl().equals(that.getProfileImgUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProfileImgUrl());
    }
}
