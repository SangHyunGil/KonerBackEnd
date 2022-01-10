package project.SangHyun.notification.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.member.domain.Member;
import project.SangHyun.notification.domain.NotificationType;
import project.SangHyun.notification.service.NotificationService;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class NotificationServiceImplTest {
    @Autowired
    NotificationService notificationService;
    @Autowired
    TestDB testDB;

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
}