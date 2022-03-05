package project.SangHyun.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.SangHyun.notification.domain.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n " +
            "where n.receiver.id = :memberId " +
            "order by n.id desc")
    List<Notification> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select count(n) from Notification n " +
            "where n.receiver.id = :memberId ")
    Long countUnReadNotifications(@Param("memberId") Long memberId);
}
