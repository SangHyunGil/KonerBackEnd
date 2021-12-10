package project.SangHyun.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Chat;
import project.SangHyun.domain.entity.ChatRoom;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "채팅 요청")
public class ChatMessageRequestDto {
    @ApiModelProperty(value = "방 ID(PK)", notes = "방 ID(PK)를 입력해주세요", required = true, example = "1")
    private Long roomId;

    @ApiModelProperty(value = "회원 ID(PK)", notes = "회원 ID(PK)를 입력해주세요", required = true, example = "1")
    private Long memberId;

    @ApiModelProperty(value = "내용", notes = "내용을 입력해주세요", required = true, example = "GilSSang")
    private String content;

    public Chat toEntity() {
        return Chat.builder()
                .member(new Member(memberId))
                .chatRoom(new ChatRoom(roomId))
                .content(content)
                .build();
    }
}
