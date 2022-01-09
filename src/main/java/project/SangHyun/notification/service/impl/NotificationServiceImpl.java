package project.SangHyun.notification.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import project.SangHyun.member.domain.Member;
import project.SangHyun.notification.domain.Notification;
import project.SangHyun.notification.domain.NotificationType;
import project.SangHyun.notification.dto.response.NotificationResponseDto;
import project.SangHyun.notification.repository.EmitterRepository;
import project.SangHyun.notification.repository.NotificationRepository;
import project.SangHyun.notification.service.NotificationService;

import java.io.IOException;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Long timeout;
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;

    public NotificationServiceImpl(@Value("${spring.sse.time}") Long timeout, NotificationRepository notificationRepository, EmitterRepository emitterRepository) {
        this.timeout = timeout;
        this.notificationRepository = notificationRepository;
        this.emitterRepository = emitterRepository;
    }

    @Override
    public SseEmitter subscribe(Member member, String lastEventId) {
        Long memberId = member.getId();
        String emitterId = memberId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 Dummy 전송
        sendNotification(emitter, emitterId, emitterId, "EventStream Created. [memberId=" + memberId + "]");

        // 캐시에 저장된 유실 Event 재전송
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberId, emitterId, emitter);
        }

        return emitter;
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("SSE")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long memberId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    @Override
    public void send(Member receiver, NotificationType notificationType, String content, String url) {
        Notification notification = notificationRepository.save(createNotification(receiver, notificationType, content, url));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationResponseDto.create(notification));
                }
        );
    }

    private Notification createNotification(Member receiver, NotificationType notificationType, String content, String url) {
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .isRead(false)
                .build();
    }
}
