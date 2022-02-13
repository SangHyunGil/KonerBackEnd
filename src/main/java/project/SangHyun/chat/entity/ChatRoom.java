package project.SangHyun.chat.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public ChatRoom(Long id) {
        this.id = id;
    }

    @Builder
    public ChatRoom(String roomName, Member member) {
        this.roomName = roomName;
        this.member = member;
    }

    public String getCreator() {
        return member.getNickname();
    }
}
