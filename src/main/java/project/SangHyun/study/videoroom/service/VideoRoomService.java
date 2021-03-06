package project.SangHyun.study.videoroom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.videoroom.domain.VideoRoom;
import project.SangHyun.study.videoroom.helper.JanusHelper;
import project.SangHyun.study.videoroom.repository.VideoRoomRepository;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomCreateDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomDeleteDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomUpdateDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomResultDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoRoomService {

    private final JanusHelper janusHelper;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final VideoRoomRepository videoRoomRepository;

    @Transactional
    public VideoRoomDto createRoom(Long studyId, VideoRoomCreateDto requestDto) {
        VideoRoomResultDto resultDto = janusHelper.postAndGetResponseDto(requestDto, VideoRoomResultDto.class);
        Member member = findMemberById(requestDto.getMemberId());
        Study study = findStudyById(studyId);
        VideoRoom videoRoom = videoRoomRepository.save(requestDto.toEntity(resultDto.getResponse().getRoom(), member, study));
        return VideoRoomDto.create(videoRoom);
    }

    @Transactional
    public void updateRoom(Long roomId, VideoRoomUpdateDto requestDto) {
        janusHelper.postAndGetResponseDto(requestDto, VideoRoomResultDto.class);
        VideoRoom videoRoom = videoRoomRepository.findByRoomId(roomId);
        videoRoom.update(requestDto.getTitle(), requestDto.getPin());
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        VideoRoomDeleteDto requestDto = VideoRoomDeleteDto.create(roomId);
        janusHelper.postAndGetResponseDto(requestDto, VideoRoomResultDto.class);
        VideoRoom videoRoom = videoRoomRepository.findByRoomId(roomId);
        videoRoomRepository.delete(videoRoom);
    }

    public List<VideoRoomDto> findRooms(Long studyId) {
        List<VideoRoom> videoRooms = videoRoomRepository.findAllByStudyId(studyId);
        return videoRooms.stream()
                .map(VideoRoomDto::create)
                .collect(Collectors.toList());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private Study findStudyById(Long studyId) {
        return studyRepository.findStudyById(studyId);
    }
}
