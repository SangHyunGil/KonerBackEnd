package project.SangHyun.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.notification.domain.Notification;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {
    private Long notificationId;
    private String content;
    private String url;
    private boolean isRead;

    public static NotificationResponseDto create(Notification notification) {
        return new NotificationResponseDto(notification.getId(), notification.getContent(),
                notification.getUrl(), notification.getIsRead());
    }
}
