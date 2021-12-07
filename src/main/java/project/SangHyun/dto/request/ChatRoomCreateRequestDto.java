package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.ChatRoom;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomCreateRequestDto {
    private String roomName;
    private Long memberId;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomName(roomName)
                .member(new Member(memberId))
                .build();
    }
}
