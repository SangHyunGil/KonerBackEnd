package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.member.domain.Member;
import project.SangHyun.notification.domain.NotificationType;
import project.SangHyun.notification.service.dto.response.NotificationDto;

import java.util.List;

class NotificationServiceTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("알림 구독을 진행한다.")
    public void subscribe() throws Exception {
        //given
        String lastEventId = "";

        //when, then
        Assertions.assertDoesNotThrow(() -> notificationService.subscribe(studyMember.getId(), lastEventId));
    }

    @Test
    @DisplayName("알림 메세지를 전송한다.")
    public void send() throws Exception {
        //given
        String lastEventId = "";
        notificationService.subscribe(studyMember.getId(), lastEventId);

        //when, then
        Assertions.assertDoesNotThrow(() -> notificationService.send(studyMember, NotificationType.APPLY, "스터디 신청에 지원하셨습니다.", "localhost:8080/study/1"));
    }

    @Test
    @DisplayName("모든 알림 메시지를 읽는다.")
    public void findAllNotifications() throws Exception {
        //given

        //when
        List<NotificationDto> notifications = notificationService.findAllNotifications(studyApplyMember.getId());

        //then
        Assertions.assertEquals(5, notifications.size());
    }
    
    @Test
    @DisplayName("읽지 않은 메시지를 개수를 조회한다.")
    public void countUnReadNotifications() throws Exception {
        //given

        //when
        Long count = notificationService.countUnReadNotifications(studyApplyMember.getId());

        //then
        Assertions.assertEquals(5, count);
    }

    @Test
    @DisplayName("메세지를 조회하면 메세지를 읽게 된다.")
    public void countUnReadNotifications2() throws Exception {
        //given

        //when
        notificationService.findAllNotifications(studyApplyMember.getId());
        persistenceContextClear();

        Long count = notificationService.countUnReadNotifications(studyApplyMember.getId());
        //then
        Assertions.assertEquals(0, count);
    }

    private void persistenceContextClear() {
        em.flush();
        em.clear();
    }
}