package project.SangHyun.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.notification.domain.Notification;
import project.SangHyun.notification.domain.NotificationType;

import java.util.List;

class NotificationRepositoryTest extends RepositoryTest {

    Member testMember;

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