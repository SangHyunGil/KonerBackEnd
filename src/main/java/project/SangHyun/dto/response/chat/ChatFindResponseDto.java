package project.SangHyun.dto.response.chat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Chat;

@Data
@NoArgsConstructor
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

    public static ChatFindResponseDto createDto(Chat chat) {
        return ChatFindResponseDto.builder()
                .roomId(chat.getId())
                .chatId(chat.getChatRoom().getId())
                .memberId(chat.getMember().getId())
                .memberName(chat.getMember().getNickname())
                .content(chat.getContent())
                .build();
    }

    @Builder
    public ChatFindResponseDto(Long roomId, Long chatId, Long memberId, String memberName, String content) {
        this.roomId = roomId;
        this.chatId = chatId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.content = content;
    }
}
