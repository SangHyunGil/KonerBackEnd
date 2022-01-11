package project.SangHyun.message.service;

import project.SangHyun.message.dto.response.MessageDeleteResponseDto;
import project.SangHyun.message.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.dto.response.MessageCreateResponseDto;
import project.SangHyun.message.dto.response.MessageFindResponseDto;

import java.util.List;

public interface MessageService {
    MessageCreateResponseDto createMessage(MessageCreateRequestDto requestDto);
    List<MessageFindResponseDto> findAllCommunicatorsWithRecentMessage(Long receiverId);
    List<MessageFindResponseDto> findAllMessages(Long senderId, Long receiverId);
    MessageDeleteResponseDto deleteBySender(Long messageId);
    MessageDeleteResponseDto deleteByReceiver(Long messageId);
}
