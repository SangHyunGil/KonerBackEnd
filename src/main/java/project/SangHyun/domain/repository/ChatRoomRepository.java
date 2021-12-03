package project.SangHyun.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.domain.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
