package project.SangHyun.dto.response.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.ChatRoom;

@Data
@NoArgsConstructor
@ApiModel(value = "채팅방 찾기 요청 결과")
public class ChatRoomFindResponseDto {
    @ApiModelProperty(value = "방 ID(PK)")
    private Long roomId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "회원 이름)")
    private String memberName;

    @ApiModelProperty(value = "방 이름")
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
