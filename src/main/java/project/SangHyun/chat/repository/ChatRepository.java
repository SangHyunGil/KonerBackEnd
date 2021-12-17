package project.SangHyun.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.chat.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @EntityGraph(attributePaths = {"chatRoom"})
    Optional<List<Chat>> findAllByChatRoomId(Long id);
}
