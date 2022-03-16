package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.factory.videoroom.VideoRoomFactory;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomCreateDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomUpdateDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomDto;

import java.util.List;

class VideoRoomServiceIntegrationTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("화상회의 방을 생성한다.")
    public void createRoom() throws Exception {
        //given
        VideoRoomCreateDto requestDto = VideoRoomFactory.createDto(studyMember.getId());

        //when
        VideoRoomDto responseDto = videoRoomService.createRoom(backendStudy.getId(), requestDto);

        //then
        Assertions.assertNotNull(videoRoomRepository.findByRoomId(responseDto.getRoomId()));

        //destroy
        videoRoomService.deleteRoom(responseDto.getRoomId());
    }

    @Test
    @DisplayName("화상회의 방을 수정한다.")
    public void editRoom() throws Exception {
        //given
        VideoRoomCreateDto createRequestDto = VideoRoomFactory.createDto(studyMember.getId());
        VideoRoomDto room = videoRoomService.createRoom(backendStudy.getId(), createRequestDto);
        VideoRoomUpdateDto updateRequestDto = VideoRoomFactory.updateDto("프론트엔드 화상회의");

        //when, then
        Assertions.assertDoesNotThrow(() -> videoRoomService.updateRoom(room.getRoomId(), updateRequestDto));

        //destroy
        videoRoomService.deleteRoom(room.getRoomId());
    }

    @Test
    @DisplayName("화상회의 방을 제거한다.")
    public void destroyRoom() throws Exception {
        //given
        VideoRoomCreateDto requestDto = VideoRoomFactory.createDto(studyMember.getId());
        VideoRoomDto room = videoRoomService.createRoom(backendStudy.getId(), requestDto);

        //when, then
        Assertions.assertDoesNotThrow(() -> videoRoomService.deleteRoom(room.getRoomId()));
    }

    @Test
    @DisplayName("화상회의 방을 모두 조회한다.")
    public void findRooms() throws Exception {
        //given
        VideoRoomCreateDto requestDto = VideoRoomFactory.createDto(studyMember.getId());
        VideoRoomDto room = videoRoomService.createRoom(backendStudy.getId(), requestDto);

        //when
        List<VideoRoomDto> rooms = videoRoomService.findRooms(backendStudy.getId());

        // then
        Assertions.assertEquals(2, rooms.size());

        //destroy
        videoRoomService.deleteRoom(room.getRoomId());
    }
}