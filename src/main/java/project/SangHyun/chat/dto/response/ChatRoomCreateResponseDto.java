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
@ApiModel(value = "채팅방 생성 요청 결과")
public class ChatRoomCreateResponseDto {
    @ApiModelProperty(value = "방 ID(PK)")
    private Long roomId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "방 제목")
    private String roomName;

    public static ChatRoomCreateResponseDto create(ChatRoom chatRoom) {
        return new ChatRoomCreateResponseDto(chatRoom.getId(), chatRoom.getMember().getId(), chatRoom.getRoomName());
    }
}
