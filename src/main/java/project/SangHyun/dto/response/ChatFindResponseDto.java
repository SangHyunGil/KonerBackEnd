package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Chat;

@Data
@NoArgsConstructor
public class ChatFindResponseDto {
    Long roomId;
    Long chatId;
    Long memberId;
    String memberName;
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
