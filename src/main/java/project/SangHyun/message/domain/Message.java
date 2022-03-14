package project.SangHyun.message.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.ApplicationEventPublisher;
import project.SangHyun.common.EntityDate;
import project.SangHyun.dto.request.NotificationRequestDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.notification.domain.NotificationType;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Embedded
    private MessageContent content;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(nullable = false)
    private Boolean deletedBySender;

    @Column(nullable = false)
    private Boolean deletedByReceiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member receiver;

    public Message(Long id) {
        this.id = id;
    }

    @Builder
    public Message(String content, Member sender, Member receiver, Boolean isRead, Boolean deletedBySender, Boolean deletedByReceiver) {
        this.content = new MessageContent(content);
        this.sender = sender;
        this.receiver = receiver;
        this.isRead = isRead;
        this.deletedBySender = deletedBySender;
        this.deletedByReceiver = deletedByReceiver;
    }

    public void deleteBySender() {
        this.deletedBySender = true;
    }

    public void deleteByReceiver() {
        this.deletedByReceiver = true;
    }

    public Boolean isDeletable() {
        return isDeletedBySender() && isDeletedByReceiver();
    }

    public boolean isDeletedBySender() {
        return deletedBySender == true;
    }

    public boolean isDeletedByReceiver() {
        return deletedByReceiver == true;
    }

    public boolean isSender(Long senderId) {
        return sender.getId() == senderId;
    }

    public Message read() {
        this.isRead = true;
        return this;
    }

    public String getContent() {
        return content.getContent();
    }

    public void publishEvent(ApplicationEventPublisher eventPublisher, NotificationType notificationType) {
        eventPublisher.publishEvent(new NotificationRequestDto(receiver, notificationType,
                notificationType.makeContent(sender.getNickname()), notificationType.makeUrl(sender.getId())));
    }
}
