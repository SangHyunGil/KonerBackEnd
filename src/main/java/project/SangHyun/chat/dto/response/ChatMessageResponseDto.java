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
@ApiModel(value = "채팅 요청 결과")
public class ChatMessageResponseDto {
    @ApiModelProperty(value = "방 ID(PK)")
    private Long roomId;

    @ApiModelProperty(value = "채팅 ID(PK)")
    private Long chatId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "회원 이름")
    private String memberName;

    @ApiModelProperty(value = "회원 내용")
    private String content;

    public static ChatMessageResponseDto create(Chat chat) {
        return new ChatMessageResponseDto(chat.getChatRoom().getId(),
                chat.getId(), chat.getMember().getId(), chat.getMember().getNickname(),
                chat.getContent());
    }
}
