package project.SangHyun.study.videoroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.SangHyun.study.videoroom.domain.VideoRoom;

import java.util.List;

public interface VideoRoomRepository extends JpaRepository<VideoRoom, Long> {

    @Query("select vr from VideoRoom vr " +
            "join fetch vr.member m " +
            "where vr.roomId.roomId = :roomId")
    VideoRoom findByRoomId(@Param("roomId") Long roomId);

    @Query("select vr from VideoRoom vr " +
            "join fetch vr.member m " +
            "where vr.study.id = :studyId")
    List<VideoRoom> findAllByStudyId(@Param("studyId") Long studyId);
}
