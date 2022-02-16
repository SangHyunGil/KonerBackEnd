package project.SangHyun.study.videoroom.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoRoom extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "videoroom_id")
    private Long id;
    @Embedded
    private VideoRoomId roomId;
    @Embedded
    private VideoRoomTitle title;
    @Embedded
    private Pin pin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Study study;

    @Builder
    public VideoRoom(Long roomId, String title, String pin, Member member, Study study) {
        this.roomId = new VideoRoomId(roomId);
        this.title = new VideoRoomTitle(title);
        this.pin = new Pin(pin);
        this.member = member;
        this.study = study;
    }

    public void update(String title, String pin) {
        this.title = new VideoRoomTitle(title);
        this.pin = new Pin(pin);
    }

    public Long getRoomId() {
        return roomId.getRoomId();
    }

    public String getTitleName() {
        return title.getTitle();
    }

    public String getPin() {
        return pin.getPin();
    }

    public Long getCreatorId() {
        return member.getId();
    }

    public String getCreatorNickname() {
        return member.getNickname();
    }

    public String getCreatorProfileImgUrl() {
        return member.getProfileImgUrl();
    }
}
