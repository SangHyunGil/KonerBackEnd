package project.SangHyun.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.message.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageCustomRepository {
}
