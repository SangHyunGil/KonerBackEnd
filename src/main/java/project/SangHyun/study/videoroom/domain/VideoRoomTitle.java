package project.SangHyun.study.videoroom.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidVideoRoomTitleException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoRoomTitle {

    private static final int MAX_LENGTH = 30;

    @Column(nullable = false, length = MAX_LENGTH)
    private String title;

    public VideoRoomTitle(String title) {
        if (isNotValidVideoRoomTitle(title)) {
            throw new InvalidVideoRoomTitleException();
        }
    }

    private boolean isNotValidVideoRoomTitle(String title) {
        return Objects.isNull(title) || title.isBlank() ||
                title.length() > MAX_LENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoRoomTitle)) return false;
        VideoRoomTitle that = (VideoRoomTitle) o;
        return getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
