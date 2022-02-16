package project.SangHyun.config.security.guard.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.SangHyun.study.videoroom.domain.VideoRoom;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;

@Component
@RequiredArgsConstructor
public class VideoRoomOwnerStrategy implements AuthStrategy {

    private final VideoRoomRepository videoRoomRepository;

    @Override
    public boolean check(Long accessMemberId, Long roomId) {
        VideoRoom videoRoom = videoRoomRepository.findByRoomId(roomId);
        return videoRoom.getCreatorId().equals(accessMemberId);
    }
}
