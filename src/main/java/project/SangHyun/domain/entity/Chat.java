package project.SangHyun.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.web.dto.ChatMessageRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    ChatRoom chatRoom;

    String content;

    public static Chat createChat(String content, ChatRoom chatRoom, Member member) {
        return Chat.builder()
                .member(member)
                .content(content)
                .chatRoom(chatRoom)
                .build();
    }

    @Builder
    public Chat(Member member, ChatRoom chatRoom, String content) {
        this.member = member;
        this.chatRoom = chatRoom;
        this.content = content;
    }
}
