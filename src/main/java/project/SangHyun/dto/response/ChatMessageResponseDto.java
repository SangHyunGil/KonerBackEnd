package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Chat;

@Data
@NoArgsConstructor
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

    public static ChatMessageResponseDto createDto(Chat chat) {
        return ChatMessageResponseDto.builder()
                .roomId(chat.getChatRoom().getId())
                .chatId(chat.getId())
                .memberId(chat.getMember().getId())
                .memberName(chat.getMember().getNickname())
                .content(chat.getContent())
                .build();
    }

    @Builder
    public ChatMessageResponseDto(Long roomId, Long chatId, Long memberId, String memberName, String content) {
        this.roomId = roomId;
        this.chatId = chatId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.content = content;
    }
}
