package project.SangHyun.message.repository;

import project.SangHyun.message.domain.Message;

import java.util.List;

public interface MessageCustomRepository {
    List<Message> findSendersWithRecentMessageDescById(Long receiverId);
    List<Message> findAllMessageWithSenderIdAndReceiverIdDescById(Long senderId, Long receiverId);
}
