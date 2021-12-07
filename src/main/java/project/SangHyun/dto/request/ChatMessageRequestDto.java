package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Chat;
import project.SangHyun.domain.entity.ChatRoom;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequestDto {
    private Long roomId;
    private Long memberId;
    private String content;

    public Chat toEntity() {
        return Chat.builder()
                .member(new Member(memberId))
                .chatRoom(new ChatRoom(roomId))
                .content(content)
                .build();
    }
}
