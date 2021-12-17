package project.SangHyun.chat.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.chat.entity.ChatRoom;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public static ChatRoomFindResponseDto create(ChatRoom chatRoom) {
        return new ChatRoomFindResponseDto(chatRoom.getId(), chatRoom.getMember().getId(),
                chatRoom.getMember().getNickname(), chatRoom.getRoomName());
    }
}
