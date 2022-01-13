package project.SangHyun.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
