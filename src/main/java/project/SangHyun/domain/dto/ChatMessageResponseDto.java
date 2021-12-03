package project.SangHyun.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Chat;

@Data
@NoArgsConstructor
public class ChatMessageResponseDto {
    private Long roomId;
    private Long chatId;
    private Long memberId;
    private String memberName;
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
