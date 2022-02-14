package project.SangHyun.study.videoroom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomCreateDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomUpdateDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoRoomService {

    private final VideoRoomRepository videoRoomRepository;

    public VideoRoomDto createRoom(VideoRoomCreateDto requestDto) {
        return null;
    }

    public VideoRoomDto updateRoom(Long roomId, VideoRoomUpdateDto editRequestDto) {
        return null;
    }

    public void deleteRoom(Long roomId) {
    }

    public List<VideoRoomDto> findRooms() {
        return null;
    }
}
