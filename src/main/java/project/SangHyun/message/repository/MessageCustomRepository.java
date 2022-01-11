package project.SangHyun.message.repository;

import project.SangHyun.message.domain.Message;

import java.util.List;

public interface MessageCustomRepository {
    List<Message> findAllCommunicatorsWithRecentMessageDescById(Long receiverId);
    List<Message> findAllMessagesWithSenderIdAndReceiverIdDescById(Long senderId, Long receiverId);
    List<Message> findAllMessagesByContent(String content);
}
