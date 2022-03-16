package project.SangHyun.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.videoroom.domain.VideoRoom;
import project.SangHyun.study.videoroom.helper.JanusHelper;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;
import project.SangHyun.study.videoroom.service.VideoRoomService;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomCreateDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomDeleteDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomUpdateDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomResultDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomDto;
import project.SangHyun.factory.videoroom.VideoRoomFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class VideoRoomServiceUnitTest {

    Member member;
    Study study;
    VideoRoom videoRoom;

    @InjectMocks
    VideoRoomService videoRoomService;
    @Mock
    JanusHelper janusHelper;
    @Mock
    MemberRepository memberRepository;
    @Mock
    StudyRepository studyRepository;
    @Mock
    VideoRoomRepository videoRoomRepository;

    @BeforeEach
    public void init() {
        member = VideoRoomFactory.makeTestAuthMember();
        study = VideoRoomFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        videoRoom =VideoRoomFactory.makeTestVideoRoom(1L, "백엔드 스터디 화상회의 방", member, study);
    }

    @Test
    @DisplayName("화상회의 방을 생성한다.")
    public void createRoom() throws Exception {
        //given
        VideoRoomCreateDto createDto = VideoRoomFactory.createDto(member.getId());
        VideoRoomResultDto resultDto = VideoRoomFactory.createResultDto();

        //mocking
        given(janusHelper.postAndGetResponseDto(createDto, VideoRoomResultDto.class)).willReturn(resultDto);
        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        given(studyRepository.findStudyById(study.getId())).willReturn(study);
        given(videoRoomRepository.save(any())).willReturn(videoRoom);

        //when
        VideoRoomDto responseDto = videoRoomService.createRoom(study.getId(), createDto);

        //then
        Assertions.assertEquals("백엔드 스터디 화상회의 방", responseDto.getTitle());
    }

    @Test
    @DisplayName("화상회의 방을 수정한다.")
    public void editRoom() throws Exception {
        //given
        VideoRoomUpdateDto updateDto = VideoRoomFactory.updateDto("프론트엔드 화상회의");
        VideoRoomResultDto resultDto = VideoRoomFactory.updateResultDto();

        //mocking
        given(janusHelper.postAndGetResponseDto(updateDto, VideoRoomResultDto.class)).willReturn(resultDto);
        given(videoRoomRepository.findByRoomId(any())).willReturn(videoRoom);

        //when, then
        Assertions.assertDoesNotThrow(() -> videoRoomService.updateRoom(videoRoom.getRoomId(), updateDto));
    }

    @Test
    @DisplayName("화상회의 방을 제거한다.")
    public void destroyRoom() throws Exception {
        //given
        VideoRoomDeleteDto deleteDto = VideoRoomFactory.deleteDto(videoRoom.getRoomId());
        VideoRoomResultDto resultDto = VideoRoomFactory.deleteResultDto();

        //mocking
        given(janusHelper.postAndGetResponseDto(deleteDto, VideoRoomResultDto.class)).willReturn(resultDto);
        given(videoRoomRepository.findByRoomId(any())).willReturn(videoRoom);
        willDoNothing().given(videoRoomRepository).delete(videoRoom);

        //when, then
        Assertions.assertDoesNotThrow(() -> videoRoomService.deleteRoom(videoRoom.getRoomId()));
    }

    @Test
    @DisplayName("화상회의 방을 모두 조회한다.")
    public void findRooms() throws Exception {
        //given

        //mocking
        given(videoRoomRepository.findAllByStudyId(study.getId())).willReturn(List.of(videoRoom));

        //when
        List<VideoRoomDto> rooms = videoRoomService.findRooms(study.getId());

        // then
        Assertions.assertEquals(1, rooms.size());
    }
}