package project.SangHyun.notification.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.notification.domain.Notification;
import project.SangHyun.notification.domain.NotificationType;

import java.util.List;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class NotificationRepositoryTest {

    Member testMember;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    NotificationRepository notificationRepository;

    @BeforeEach
    public void init() {
        Member member = new Member("xptmxm6!", passwordEncoder.encode("xptmxm6!"), "길쌍", Department.CSE, "profileImgUrl", MemberRole.ROLE_MEMBER, "길쌍입니다.");
        testMember = memberRepository.save(member);

        for (int i = 0; i < 5; i++) {
            Notification notification = new Notification(member, NotificationType.APPLY, "신청", "/study/51", false);
            notificationRepository.save(notification);
        }
    }

    @Test
    @DisplayName("모든 알림을 찾는다.")
    public void test1() throws Exception {
        //given

        //when
        List<Notification> notifications = notificationRepository.findAllByMemberId(testMember.getId());

        //then
        Assertions.assertEquals(5, notifications.size());
    }

    @Test
    @DisplayName("안읽은 알림의 개수를 찾는다.")
    public void test2() throws Exception {
        //given


        //when
        Long count = notificationRepository.countUnReadNotifications(testMember.getId());

        //then
        Assertions.assertEquals(5, count);
    }
}