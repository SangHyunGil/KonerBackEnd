package project.SangHyun.study.videoroom.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.advice.exception.InvalidVideoRoomIdException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoRoomId {

    @Column(nullable = false)
    private Long roomId;

    public VideoRoomId(Long roomId) {
        if (isNotValidVideoRoomId(roomId)) {
            throw new InvalidVideoRoomIdException();
        }
    }

    private boolean isNotValidVideoRoomId(Long roomId) {
        return Objects.isNull(roomId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoRoomId)) return false;
        VideoRoomId videoRoomId1 = (VideoRoomId) o;
        return getRoomId().equals(videoRoomId1.getRoomId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomId());
    }
}
