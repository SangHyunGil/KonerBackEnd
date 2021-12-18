package project.SangHyun.chat.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.chat.entity.ChatRoom;
import project.SangHyun.member.domain.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "채팅방 생성 요청")
public class ChatRoomCreateRequestDto {
    @ApiModelProperty(value = "방 제목", notes = "방 제목을 입력해주세요", required = true, example = "GilSSang")
    private String roomName;

    @ApiModelProperty(value = "회원 ID(PK)", notes = "회원 ID(PK)를 입력해주세요", required = true, example = "1")
    private Long memberId;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .roomName(roomName)
                .member(new Member(memberId))
                .build();
    }
}
