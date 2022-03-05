package project.SangHyun.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MessageNotFountException;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.MessageRepository;
import project.SangHyun.message.repository.impl.RecentMessageDto;
import project.SangHyun.message.service.dto.request.MessageCreateDto;
import project.SangHyun.message.service.dto.response.FindCommunicatorsDto;
import project.SangHyun.message.service.dto.response.MessageDto;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;

    @Transactional
    public MessageDto createMessage(MessageCreateDto requestDto) {
        Message message = messageRepository.save(requestDto.toEntity());
        return MessageDto.create(message);
    }

    public List<FindCommunicatorsDto> findAllCommunicatorsWithRecentMessage(Long receiverId) {
        List<RecentMessageDto> messages = messageRepository.findAllCommunicatorsWithRecentMessageDescById(receiverId);
        return messages.stream()
                .map(FindCommunicatorsDto::create)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MessageDto> findAllMessages(Long senderId, Long receiverId) {
        List<Message> messages = messageRepository.findAllMessagesWithSenderIdAndReceiverIdDescById(senderId, receiverId);
        messages.stream()
                .filter(message -> message.isSender(senderId))
                .forEach(Message::read);
        return messages.stream()
                .map(MessageDto::create)
                .collect(Collectors.toList());
    }

    public Long countUnReadMessages(Long receiverId) {
        return messageRepository.countAllUnReadMessages(receiverId);
    }

    @Transactional
    public void deleteBySender(Long messageId) {
        Message message = findMessageById(messageId);
        deleteMessage(message, Message::deleteBySender);
    }

    @Transactional
    public void deleteByReceiver(Long messageId) {
        Message message = findMessageById(messageId);
        deleteMessage(message, Message::deleteByReceiver);
    }

    private Message findMessageById(Long messageId) {
        return messageRepository.findById(messageId).orElseThrow(MessageNotFountException::new);
    }

    private void deleteMessage(Message message, Consumer<Message> delete) {
        delete.accept(message);
        if (message.isDeletable())
            messageRepository.delete(message);
    }
}
