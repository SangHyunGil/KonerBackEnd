package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.notification.domain.NotificationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private Member receiver;
    private NotificationType notificationType;
    private String content;
    private String url;
}
