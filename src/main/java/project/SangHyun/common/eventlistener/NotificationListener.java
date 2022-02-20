package project.SangHyun.common.eventlistener;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import project.SangHyun.common.dto.request.NotificationRequestDto;
import project.SangHyun.notification.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    @Async
    public void handleNotification(NotificationRequestDto requestDto) {
        notificationService.send(requestDto.getReceiver(), requestDto.getNotificationType(),
                requestDto.getContent(), requestDto.getUrl());
    }
}
