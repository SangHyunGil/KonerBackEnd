package project.SangHyun.study.videoroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.SangHyun.study.videoroom.domain.VideoRoom;

public interface VideoRoomRepository extends JpaRepository<VideoRoom, Long> {
}
