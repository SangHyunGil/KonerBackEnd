package project.SangHyun.message.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member receiver;
    private Boolean deletedBySender;
    private Boolean deletedByReceiver;

    public Message(Long id) {
        this.id = id;
    }

    @Builder
    public Message(String content, Member sender, Member receiver, Boolean deletedBySender, Boolean deletedByReceiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
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

    public boolean isMoreRecentlyThan(Message message) {
        return this.id > message.getId();
    }
}
