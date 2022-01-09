package project.SangHyun.notification.domain;

import lombok.*;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Notification extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member receiver;
    private NotificationType notificationType;
    private String content;
    private String url;
    private Boolean isRead;

    @Builder
    public Notification(Member receiver, NotificationType notificationType, String content, String url, Boolean isRead) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = content;
        this.url = url;
        this.isRead = isRead;
    }
}
