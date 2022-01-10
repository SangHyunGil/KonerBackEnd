package project.SangHyun.message.repository.impl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.MessageCustomRepository;

import java.util.List;

import static project.SangHyun.member.domain.QMember.member;
import static project.SangHyun.message.domain.QMessage.message;

@RequiredArgsConstructor
public class MessageCustomRepositoryImpl implements MessageCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Message> findSendersWithRecentMessageDescById(Long receiverId) {
        return jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .where(message.id.in(
                        JPAExpressions
                                .select(message.id.max())
                                .from(message)
                                .groupBy(message.sender)))
                .orderBy(message.id.desc())
                .fetch();
    }

    @Override
    public List<Message> findAllMessageWithSenderIdAndReceiverIdDescById(Long senderId, Long receiverId) {
        return jpaQueryFactory.selectFrom(message)
                .innerJoin(message.sender, member).fetchJoin()
                .innerJoin(message.receiver, member).fetchJoin()
                .where(message.sender.id.eq(senderId),
                        message.receiver.id.eq(receiverId))
                .orderBy(message.id.desc())
                .fetch();
    }
}
