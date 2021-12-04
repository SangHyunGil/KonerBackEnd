package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.ChatRoom;

@Data
@NoArgsConstructor
public class ChatRoomCreateResponseDto {
    private Long roomId;
    private Long memberId;
    private String roomName;

    public static ChatRoomCreateResponseDto createDto(ChatRoom chatRoom) {
        return ChatRoomCreateResponseDto.builder()
                .roomId(chatRoom.getId())
                .roomName(chatRoom.getRoomName())
                .memberId(chatRoom.getMember().getId())
                .build();
    }

    @Builder
    public ChatRoomCreateResponseDto(Long roomId, Long memberId, String roomName) {
        this.roomId = roomId;
        this.memberId = memberId;
        this.roomName = roomName;
    }
}
