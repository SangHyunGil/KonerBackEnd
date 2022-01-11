package project.SangHyun.message.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.dto.response.MessageCreateResponseDto;
import project.SangHyun.message.dto.response.MessageDeleteResponseDto;
import project.SangHyun.message.dto.response.MessageFindResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class MessageFactory extends BasicFactory {
    // Request
    public static MessageCreateRequestDto makeCreateRequestDto(Long id, Member sender, Member receiver) {
        return new MessageCreateRequestDto(sender.getId(), receiver.getId(), "테스트 쪽지입니다.");
    }

    // Response
    public static MessageCreateResponseDto makeCreateResponseDto(Message message) {
        return MessageCreateResponseDto.create(message);
    }

    public static List<MessageFindResponseDto> makeFindResponseDto(List<Message> messages) {
        return messages.stream()
                .map(message -> MessageFindResponseDto.create(message))
                .collect(Collectors.toList());
    }

    public static MessageDeleteResponseDto makeDeleteResponseDto(Message message) {
        return MessageDeleteResponseDto.create(message);
    }
}
