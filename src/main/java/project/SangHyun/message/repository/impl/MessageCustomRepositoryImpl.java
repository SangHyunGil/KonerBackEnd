package project.SangHyun.message.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.member.domain.QMember;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.MessageCustomRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.core.types.dsl.Expressions.constant;
import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.message.domain.QMessage.message;

@RequiredArgsConstructor
public class MessageCustomRepositoryImpl implements MessageCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MessageCount> findAllCommunicatorsWithRecentMessageDescById(Long memberId) {
        List<MessageCount> receiveMessages = jpaQueryFactory
                .select(Projections.constructor(MessageCount.class,
                        message.id, message.sender, message.receiver,
                        message.content.content, as(constant(0L),"unReadCount")))
                .from(message)
                .where(message.id.in(
                        JPAExpressions
                                .select(message.id.max())
                                .from(message)
                                .where(equalsWithMemberId(memberId, message.receiver))
                                .groupBy(message.sender, message.receiver)))
                .fetch();

        List<MessageCount> sendMessages = jpaQueryFactory
                .select(Projections.constructor(MessageCount.class,
                        message.id, message.sender, message.receiver,
                        message.content.content, as(constant(0L),"unReadCount")))
                .from(message)
                .where(message.id.in(
                        JPAExpressions
                                .select(message.id.max())
                                .from(message)
                                .where(equalsWithMemberId(memberId, message.sender))
                                .groupBy(message.sender, message.receiver)))
                .fetch();

        List<MessageCount> countMessages = jpaQueryFactory
                .select(Projections.constructor(MessageCount.class,
                        message.id, message.sender, message.receiver,
                        message.content.content, message.count()))
                .from(message)
                .where(equalsWithMemberId(memberId, message.receiver),
                        message.isRead.eq(false))
                .groupBy(message.sender, message.receiver)
                .fetch();

        return findRecentCommunicatorsByCompareSendAndReceiveMessage(receiveMessages, sendMessages, countMessages);

    }

    private BooleanExpression equalsWithMemberId(Long memberId, QMember receiver) {
        return receiver.id.eq(memberId);
    }

    private List<MessageCount> findRecentCommunicatorsByCompareSendAndReceiveMessage(List<MessageCount> receiveMessages,
                                                                                List<MessageCount> sendMessages, List<MessageCount> countMessages) {
        Map<Long, MessageCount> store = new HashMap<>();
        // 받은 메세지 중 최신 메세지 기록
        receiveMessages
                .forEach(receiveMessage -> store.put(receiveMessage.getSender().getId(), receiveMessage));

        // 전송한 메세지 중 받은 메세지의 최신 메세지와 비교 후 갱신
        sendMessages
                .forEach(sendMessage -> compareAndUpdate(store, sendMessage));

        // 안읽은 메세지 개수 갱신
        countMessages
                .forEach(countMessage -> {
                    MessageCount messageCount = store.get(countMessage.getSender().getId());
                    messageCount.setUnReadCount(countMessage.getUnReadCount());
                    System.out.println("messageCount = " + messageCount);
                });

        return store.values().stream()
                .sorted(Comparator.comparing(MessageCount::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private void compareAndUpdate(Map<Long, MessageCount> store, MessageCount sendMessage) {
        Long senderId = sendMessage.getReceiver().getId();
        MessageCount message = store.getOrDefault(senderId, new MessageCount(Long.MIN_VALUE));
        if (sendMessage.isMoreRecentlyThan(message))
            store.put(senderId, sendMessage);
    }


    @Override
    public List<Message> findAllMessagesWithSenderIdAndReceiverIdDescById(Long senderId, Long receiverId) {
        return jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .innerJoin(message.receiver, member).fetchJoin()
                .where((equalsWithMemberId(senderId, message.sender).and(
                        equalsWithMemberId(receiverId, message.receiver)))
                        .or(equalsWithMemberId(receiverId, message.sender).and(
                                equalsWithMemberId(senderId, message.receiver))))
                .orderBy(message.id.desc())
                .fetch();
    }

    @Override
    public List<Message> findAllMessagesByContent(String content) {
        return jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .innerJoin(message.receiver, member).fetchJoin()
                .where(message.content.content.contains(content))
                .orderBy(message.id.desc())
                .fetch();
    }

    @Override
    public Long countAllUnReadMessages(Long receiverId) {
        return jpaQueryFactory.selectFrom(message)
                .where(equalsWithMemberId(receiverId, message.receiver))
                .fetchCount();
    }
}
