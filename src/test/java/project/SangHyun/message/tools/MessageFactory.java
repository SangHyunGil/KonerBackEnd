package project.SangHyun.message.tools;

import project.SangHyun.BasicFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.message.controller.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.controller.dto.response.FindCommunicatorsResponseDto;
import project.SangHyun.message.controller.dto.response.MessageResponseDto;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.impl.RecentMessageDto;
import project.SangHyun.message.service.dto.request.MessageCreateDto;
import project.SangHyun.message.service.dto.response.FindCommunicatorsDto;
import project.SangHyun.message.service.dto.response.MessageDto;

public class MessageFactory extends BasicFactory {
    // Request
    public static MessageCreateRequestDto makeCreateRequestDto(Long id, Member sender, Member receiver) {
        return new MessageCreateRequestDto(sender.getId(), receiver.getId(), "테스트 쪽지입니다.");
    }

    public static MessageCreateDto makeCreateDto(Long id, Member sender, Member receiver) {
        return new MessageCreateDto(sender.getId(), receiver.getId(), "테스트 쪽지입니다.");
    }

    // Response
    public static MessageDto makeMessageDto(Message message) {
        return MessageDto.create(message);
    }

    public static MessageResponseDto makeMessageResponseDto(MessageDto messageDto) {
        return MessageResponseDto.create(messageDto);
    }

    public static FindCommunicatorsDto makeFindCommunicatorsDto(RecentMessageDto recentMessageDto) {
        return FindCommunicatorsDto.create(recentMessageDto);
    }

    public static FindCommunicatorsResponseDto makeFindCommunicatorsResponseDto(FindCommunicatorsDto findCommunicatorsDto) {
        return FindCommunicatorsResponseDto.create(findCommunicatorsDto);
    }
}
