package project.SangHyun.study.videoroom.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private VideoRoomId roomId;
    @Embedded
    private VideoRoomTitle title;
    @Embedded
    private Pin pin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public VideoRoom(Long roomId, String title, String pin, Member member) {
        this.roomId = new VideoRoomId(roomId);
        this.title = new VideoRoomTitle(title);
        this.pin = new Pin(pin);
        this.member = member;
    }

    public Long getRoomId() {
        return roomId.getRoomId();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getPin() {
        return pin.getPin();
    }

    public String getCreatorNickname() {
        return member.getNickname();
    }

    public String getCreatorProfileImgUrl() {
        return member.getProfileImgUrl();
    }
}
