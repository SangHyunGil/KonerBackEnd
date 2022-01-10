package project.SangHyun.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import project.SangHyun.member.domain.Member;
import project.SangHyun.notification.domain.NotificationType;

public interface NotificationService {
    SseEmitter subscribe(Long memberId, String lastEventId);
    void send(Member receiver, NotificationType notificationType, String content, String url);
}
