package project.SangHyun.message.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.MessageCustomRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.core.types.dsl.Expressions.constant;
import static java.util.Comparator.comparing;
import static project.SangHyun.helper.BooleanBuilderHelper.nullSafeBuilder;
import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.message.domain.QMessage.message;

@RequiredArgsConstructor
public class MessageCustomRepositoryImpl implements MessageCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RecentMessageDto> findAllCommunicatorsWithRecentMessageDescById(Long memberId) {
        List<RecentMessageDto> receiveMessages = jpaQueryFactory
                .select(Projections.constructor(RecentMessageDto.class,
                        message.id, message.sender, message.receiver,
                        message.content.content, as(constant(0L),"unReadCount")))
                .from(message)
                .where(message.id.in(
                        JPAExpressions
                                .select(message.id.max())
                                .from(message)
                                .where(equalsWithReceiverId(memberId))
                                .groupBy(message.sender, message.receiver)))
                .fetch();

        List<RecentMessageDto> sendMessages = jpaQueryFactory
                .select(Projections.constructor(RecentMessageDto.class,
                        message.id, message.sender, message.receiver,
                        message.content.content, as(constant(0L),"unReadCount")))
                .from(message)
                .where(message.id.in(
                        JPAExpressions
                                .select(message.id.max())
                                .from(message)
                                .where(equalsWithSenderId(memberId))
                                .groupBy(message.sender, message.receiver)))
                .fetch();

        List<RecentMessageDto> countMessages = jpaQueryFactory
                .select(Projections.constructor(RecentMessageDto.class,
                        message.id, message.sender, message.receiver,
                        message.content.content, message.count()))
                .from(message)
                .where(equalsWithReceiverId(memberId),
                        isUnReadMessage())
                .groupBy(message.sender, message.receiver)
                .fetch();

        return findRecentCommunicatorsByCompareSendAndReceiveMessage(receiveMessages, sendMessages, countMessages);

    }

    @Override
    public List<Message> findAllMessagesWithSenderIdAndReceiverIdDescById(Long senderId, Long receiverId) {
        return jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .innerJoin(message.receiver, member).fetchJoin()
                .where((equalsWithSenderId(senderId).and(equalsWithReceiverId(receiverId))).or
                       (equalsWithSenderId(receiverId).and(equalsWithReceiverId(senderId))))
                .orderBy(message.id.desc())
                .fetch();
    }

    @Override
    public List<Message> findAllMessagesByContent(String content) {
        return jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .innerJoin(message.receiver, member).fetchJoin()
                .where(containContent(content))
                .orderBy(message.id.desc())
                .fetch();
    }

    @Override
    public Long countAllUnReadMessages(Long receiverId) {
        return jpaQueryFactory.selectFrom(message)
                .where(equalsWithReceiverId(receiverId),
                        isUnReadMessage())
                .fetchCount();
    }

    private BooleanExpression equalsWithSenderId(Long memberId) {
        return message.sender.id.eq(memberId);
    }

    private BooleanExpression equalsWithReceiverId(Long memberId) {
        return message.receiver.id.eq(memberId);
    }

    private List<RecentMessageDto> findRecentCommunicatorsByCompareSendAndReceiveMessage(List<RecentMessageDto> receiveMessages,
                                                                                         List<RecentMessageDto> sendMessages, List<RecentMessageDto> countMessages) {
        Map<Member, RecentMessageDto> store = new HashMap<>();

        // 보낸 메세지 중 최신 메세지 기록
        receiveMessages
                .forEach(receiveMessage -> store.put(receiveMessage.getSender(), receiveMessage));

        // 전송한 메세지의 최신 메세지와 받은 메세지의 최신 메세지를 비교한 후 더 최신의 것으로 갱신
        sendMessages
                .forEach(sendMessage -> compareAndUpdate(store, sendMessage));

        // 안읽은 메세지 개수 갱신
        countMessages
                .forEach(countMessage -> {
                    RecentMessageDto messageCount = store.get(countMessage.getSender());
                    messageCount.setUnReadCount(countMessage.getUnReadCount());
                });

        return store.values().stream()
                .sorted(comparing(RecentMessageDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private void compareAndUpdate(Map<Member, RecentMessageDto> store, RecentMessageDto sendMessage) {
        Member receiver = sendMessage.getReceiver();
        RecentMessageDto message = store.getOrDefault(receiver, new RecentMessageDto(Long.MIN_VALUE));
        if (sendMessage.isMoreRecentlyThan(message))
            store.put(receiver, sendMessage);
    }

    private BooleanBuilder isUnReadMessage() {
        return nullSafeBuilder(() -> message.isRead.eq(false));
    }

    private BooleanBuilder containContent(String content) {
        return nullSafeBuilder(() -> message.content.content.contains(content));
    }
}
