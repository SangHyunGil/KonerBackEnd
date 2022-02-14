package project.SangHyun.study.videoroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.SangHyun.study.videoroom.domain.VideoRoom;

public interface VideoRoomRepository extends JpaRepository<VideoRoom, Long> {

    @Query("select vr from VideoRoom vr " +
            "join fetch vr.member m " +
            "where vr.roomId.roomId = :roomId")
    VideoRoom findByRoomId(@Param("roomId") Long roomId);
}
