package project.SangHyun.message.repository;

import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.impl.MessageDto;

import java.util.List;

public interface MessageCustomRepository {
    List<MessageDto> findAllCommunicatorsWithRecentMessageDescById(Long receiverId);
    Long countAllUnReadMessages(Long receiverId);
    List<Message> findAllMessagesWithSenderIdAndReceiverIdDescById(Long senderId, Long receiverId);
    List<Message> findAllMessagesByContent(String content);
}
