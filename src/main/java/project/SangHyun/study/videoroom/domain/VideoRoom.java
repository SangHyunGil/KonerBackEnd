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
    private VideoRoomId videoRoomId;
    @Embedded
    private VideoRoomTitle description;
    @Embedded
    private Pin pin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public VideoRoom(Long roomId, String description, String pin, Member member) {
        this.videoRoomId = new VideoRoomId(roomId);
        this.description = new VideoRoomTitle(description);
        this.pin = new Pin(pin);
        this.member = member;
    }
}
