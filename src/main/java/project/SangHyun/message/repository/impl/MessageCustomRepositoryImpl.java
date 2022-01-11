package project.SangHyun.message.repository.impl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.MessageCustomRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.message.domain.QMessage.message;

@RequiredArgsConstructor
public class MessageCustomRepositoryImpl implements MessageCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Message> findAllCommunicatorsWithRecentMessageDescById(Long memberId) {
        List<Message> receiveMessages = jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .where(message.id.in(
                        JPAExpressions
                                .select(message.id.max())
                                .from(message)
                                .where(message.receiver.id.eq(memberId))
                                .groupBy(message.sender, message.receiver)))
                .fetch();

        List<Message> sendMessages = jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .where(message.id.in(
                        JPAExpressions
                                .select(message.id.max())
                                .from(message)
                                .where(message.sender.id.eq(memberId))
                                .groupBy(message.sender, message.receiver)))
                .fetch();

        return findRecentCommunicatorsByCompareSendAndReceiveMessage(receiveMessages, sendMessages);

    }

    private List<Message> findRecentCommunicatorsByCompareSendAndReceiveMessage(List<Message> receiveMessages, List<Message> sendMessages) {
        Map<Long, Message> store = new HashMap<>();
        // 받은 메세지 중 최신 메세지 기록
        receiveMessages
                .forEach(receiveMessage -> store.put(receiveMessage.getSender().getId(), receiveMessage));
        // 전송한 메세지 중 최신 메세지 비교 후 갱신
        sendMessages
                .forEach(sendMessage -> compareAndUpdate(store, sendMessage));
        return store.values().stream()
                .sorted(Comparator.comparing(Message::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private void compareAndUpdate(Map<Long, Message> store, Message sendMessage) {
        Long senderId = sendMessage.getReceiver().getId();
        Message message = store.getOrDefault(senderId, new Message(Long.MIN_VALUE));
        if (sendMessage.isMoreRecentlyThan(message))
            store.put(senderId, sendMessage);
    }


    @Override
    public List<Message> findAllMessagesWithSenderIdAndReceiverIdDescById(Long senderId, Long receiverId) {
        return jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .innerJoin(message.receiver, member).fetchJoin()
                .where((message.sender.id.eq(senderId).and(
                        message.receiver.id.eq(receiverId)))
                        .or(message.sender.id.eq(receiverId).and(
                                message.receiver.id.eq(senderId))))
                .orderBy(message.id.desc())
                .fetch();
    }

    @Override
    public List<Message> findAllMessagesByContent(String content) {
        return jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .innerJoin(message.receiver, member).fetchJoin()
                .where(message.content.contains(content))
                .orderBy(message.id.desc())
                .fetch();
    }
}
