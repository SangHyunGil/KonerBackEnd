package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;
import project.SangHyun.study.videoroom.service.VideoRoomService;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomCreateDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomUpdateDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomDto;
import project.SangHyun.factory.videoroom.VideoRoomFactory;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class VideoRoomServiceIntegrationTest {
    Member member;
    Study study;

    @Autowired
    VideoRoomService videoRoomService;
    @Autowired
    VideoRoomRepository videoRoomRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    public void init() {
        testDB.init();
        member = testDB.findStudyGeneralMember();
        study = testDB.findBackEndStudy();
    }

    @Test
    @DisplayName("화상회의 방을 생성한다.")
    public void createRoom() throws Exception {
        //given
        VideoRoomCreateDto requestDto = VideoRoomFactory.createDto(member.getId());

        //when
        VideoRoomDto responseDto = videoRoomService.createRoom(study.getId(), requestDto);

        //then
        Assertions.assertNotNull(videoRoomRepository.findByRoomId(responseDto.getRoomId()));

        //destroy
        videoRoomService.deleteRoom(responseDto.getRoomId());
    }

    @Test
    @DisplayName("화상회의 방을 수정한다.")
    public void editRoom() throws Exception {
        //given
        VideoRoomCreateDto createRequestDto = VideoRoomFactory.createDto(member.getId());
        VideoRoomDto room = videoRoomService.createRoom(study.getId(), createRequestDto);
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
        VideoRoomCreateDto requestDto = VideoRoomFactory.createDto(member.getId());
        VideoRoomDto room = videoRoomService.createRoom(study.getId(), requestDto);

        //when, then
        Assertions.assertDoesNotThrow(() -> videoRoomService.deleteRoom(room.getRoomId()));
    }

    @Test
    @DisplayName("화상회의 방을 모두 조회한다.")
    public void findRooms() throws Exception {
        //given
        VideoRoomCreateDto requestDto = VideoRoomFactory.createDto(member.getId());
        VideoRoomDto room = videoRoomService.createRoom(study.getId(), requestDto);

        //when
        List<VideoRoomDto> rooms = videoRoomService.findRooms(study.getId());

        // then
        Assertions.assertEquals(2, rooms.size());

        //destroy
        videoRoomService.deleteRoom(room.getRoomId());
    }
}