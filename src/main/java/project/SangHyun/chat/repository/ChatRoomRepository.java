package project.SangHyun.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
