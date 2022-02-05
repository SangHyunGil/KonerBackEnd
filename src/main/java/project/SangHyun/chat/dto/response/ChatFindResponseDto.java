package project.SangHyun.chat.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.chat.entity.Chat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "채팅 찾기 요청 결과")
public class ChatFindResponseDto {
    @ApiModelProperty(value = "방 ID(PK)")
    Long roomId;

    @ApiModelProperty(value = "채팅 ID(PK)")
    Long chatId;

    @ApiModelProperty(value = "회원 ID(PK)")
    Long memberId;

    @ApiModelProperty(value = "회원 이름")
    String memberName;

    @ApiModelProperty(value = "내용")
    String content;

    public static ChatFindResponseDto create(Chat chat) {
        return new ChatFindResponseDto(chat.getChatRoom().getId(), chat.getId(),
                chat.getMember().getId(), chat.getSender(), chat.getContent());
    }
}
