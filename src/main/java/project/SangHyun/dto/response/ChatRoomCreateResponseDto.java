package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.ChatRoom;

@Data
@NoArgsConstructor
@ApiModel(value = "채팅방 생성 요청 결과")
public class ChatRoomCreateResponseDto {
    @ApiModelProperty(value = "방 ID(PK)")
    private Long roomId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "방 제목")
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
