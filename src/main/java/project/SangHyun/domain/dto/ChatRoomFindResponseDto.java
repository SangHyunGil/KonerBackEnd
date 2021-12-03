package project.SangHyun.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.ChatRoom;

@Data
@NoArgsConstructor
public class ChatRoomFindResponseDto {
    private Long roomId;
    private Long memberId;
    private String memberName;
    private String roomName;

    public static ChatRoomFindResponseDto createDto(ChatRoom chatRoom) {
        return ChatRoomFindResponseDto.builder()
                .roomId(chatRoom.getId())
                .roomName(chatRoom.getRoomName())
                .memberId(chatRoom.getMember().getId())
                .memberName(chatRoom.getMember().getNickname())
                .build();
    }

    @Builder
    public ChatRoomFindResponseDto(Long roomId, Long memberId, String memberName, String roomName) {
        this.roomId = roomId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.roomName = roomName;
    }
}
