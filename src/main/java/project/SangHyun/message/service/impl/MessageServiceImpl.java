package project.SangHyun.message.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MessageNotFountException;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.dto.response.CommunicatorFindResponseDto;
import project.SangHyun.message.dto.response.MessageCreateResponseDto;
import project.SangHyun.message.dto.response.MessageDeleteResponseDto;
import project.SangHyun.message.dto.response.MessageFindResponseDto;
import project.SangHyun.message.repository.MessageRepository;
import project.SangHyun.message.repository.impl.MessageCount;
import project.SangHyun.message.service.MessageService;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public MessageCreateResponseDto createMessage(MessageCreateRequestDto requestDto) {
        Message message = messageRepository.save(requestDto.toEntity());
        return MessageCreateResponseDto.create(message);
    }

    @Override
    public List<CommunicatorFindResponseDto> findAllCommunicatorsWithRecentMessage(Long receiverId) {
        List<MessageCount> messages = messageRepository.findAllCommunicatorsWithRecentMessageDescById(receiverId);
        return messages.stream()
                .map(message -> CommunicatorFindResponseDto.create(message))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MessageFindResponseDto> findAllMessages(Long senderId, Long receiverId) {
        List<Message> messages = messageRepository.findAllMessagesWithSenderIdAndReceiverIdDescById(senderId, receiverId);
        return messages.stream()
                .map(message -> message.read())
                .map(message -> MessageFindResponseDto.create(message))
                .collect(Collectors.toList());
    }

    @Override
    public Long countUnReadMessages(Long receiverId) {
        return messageRepository.countAllUnReadMessages(receiverId);
    }

    @Override
    @Transactional
    public MessageDeleteResponseDto deleteBySender(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFountException::new);
        deleteMessage(message, Message::deleteBySender);
        return MessageDeleteResponseDto.create(message);
    }

    @Override
    @Transactional
    public MessageDeleteResponseDto deleteByReceiver(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFountException::new);
        deleteMessage(message, Message::deleteByReceiver);
        return MessageDeleteResponseDto.create(message);
    }

    private void deleteMessage(Message message, Consumer<Message> delete) {
        delete.accept(message);
        if (message.isDeletable())
            messageRepository.delete(message);
    }
}
