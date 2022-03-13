package project.SangHyun.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.study.videoroom.helper.JanusHelper;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomCreateDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomDeleteDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomUpdateDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomResultDto;
import project.SangHyun.factory.videoroom.VideoRoomFactory;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class JanusHelperTest {

    @Autowired
    JanusHelper janusHelper;

    @Test
    @DisplayName("Janus Create Post를 진행한다.")
    public void createPost() throws Exception {
        //given
        VideoRoomCreateDto requestDto = VideoRoomFactory.createDto(1L);

        //when
        VideoRoomResultDto resultDto = janusHelper.postAndGetResponseDto(requestDto, VideoRoomResultDto.class);

        //then
        Assertions.assertNotNull(resultDto);

        //destroy
        VideoRoomDeleteDto destroyRequestDto = VideoRoomFactory.deleteDto(resultDto.getResponse().getRoom());
        janusHelper.postAndGetResponseDto(destroyRequestDto, VideoRoomResultDto.class);
    }

    @Test
    @DisplayName("Janus Edit Post를 진행한다.")
    public void editPost() throws Exception {
        //given
        VideoRoomCreateDto createRequestDto = VideoRoomFactory.createDto(1L);
        janusHelper.postAndGetResponseDto(createRequestDto, VideoRoomResultDto.class);
        VideoRoomUpdateDto editRequestDto = VideoRoomFactory.updateDto("프론트엔드 화상회의");

        //when
        VideoRoomResultDto editResultDto = janusHelper.postAndGetResponseDto(editRequestDto, VideoRoomResultDto.class);

        //then
        Assertions.assertNotNull(editResultDto);
    }

    @Test
    @DisplayName("Janus Destroy Post를 진행한다.")
    public void DestroyPost() throws Exception {
        VideoRoomCreateDto createRequestDto = VideoRoomFactory.createDto(1L);
        VideoRoomResultDto createResultDto = janusHelper.postAndGetResponseDto(createRequestDto, VideoRoomResultDto.class);
        VideoRoomDeleteDto destroyRequestDto = VideoRoomFactory.deleteDto(createResultDto.getResponse().getRoom());

        //when
        VideoRoomResultDto destroyResultDto = janusHelper.postAndGetResponseDto(destroyRequestDto, VideoRoomResultDto.class);

        //then
        Assertions.assertNotNull(destroyResultDto);
    }
}