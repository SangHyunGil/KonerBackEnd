package project.SangHyun.notification.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.member.domain.Member;
import project.SangHyun.notification.domain.NotificationType;
import project.SangHyun.notification.service.NotificationService;
import project.SangHyun.notification.service.dto.response.NotificationDto;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @Autowired
    TestDB testDB;

    @Autowired
    EntityManager em;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("알림 구독을 진행한다.")
    public void subscribe() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String lastEventId = "";

        //when, then
        Assertions.assertDoesNotThrow(() -> notificationService.subscribe(member.getId(), lastEventId));
    }

    @Test
    @DisplayName("알림 메세지를 전송한다.")
    public void send() throws Exception {
        //given
        Member member = testDB.findGeneralMember();
        String lastEventId = "";
        notificationService.subscribe(member.getId(), lastEventId);

        //when, then
        Assertions.assertDoesNotThrow(() -> notificationService.send(member, NotificationType.APPLY, "스터디 신청에 지원하셨습니다.", "localhost:8080/study/1"));
    }

    @Test
    @DisplayName("모든 알림 메시지를 읽는다.")
    public void findAllNotifications() throws Exception {
        //given
        Member member = testDB.findNotStudyMember();

        //when
        List<NotificationDto> notifications = notificationService.findAllNotifications(member.getId());

        //then
        Assertions.assertEquals(5, notifications);
    }
    
    @Test
    @DisplayName("읽지 않은 메시지를 개수를 조회한다.")
    public void countUnReadNotifications() throws Exception {
        //given
        Member member = testDB.findNotStudyMember();

        //when
        Long count = notificationService.countUnReadNotifications(member.getId());

        //then
        Assertions.assertEquals(5, count);
    }

    @Test
    @DisplayName("메세지를 조회하면 메세지를 읽게 된다.")
    public void countUnReadNotifications2() throws Exception {
        //given
        Member member = testDB.findNotStudyMember();

        //when
        notificationService.findAllNotifications(member.getId());
        persistenceContextClear();

        Long count = notificationService.countUnReadNotifications(member.getId());
        //then
        Assertions.assertEquals(0, count);
    }

    private void persistenceContextClear() {
        em.flush();
        em.clear();
    }
}