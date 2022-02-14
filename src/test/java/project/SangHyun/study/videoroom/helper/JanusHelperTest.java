package project.SangHyun.study.videoroom.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.study.videoroom.service.dto.request.CreateRoomRequestDto;
import project.SangHyun.study.videoroom.service.dto.request.DestroyRoomRequestDto;
import project.SangHyun.study.videoroom.service.dto.request.EditRoomRequestDto;
import project.SangHyun.study.videoroom.service.dto.response.CreateRoomResultDto;
import project.SangHyun.study.videoroom.service.dto.response.DestroyRoomResultDto;
import project.SangHyun.study.videoroom.service.dto.response.EditRoomResultDto;
import project.SangHyun.study.videoroom.tools.RoomTestCaseFactory;

@SpringBootTest
@Transactional
class JanusHelperTest {
    @Autowired
    JanusHelper janusHelper;

    @Test
    public void createPost() throws Exception {
        //given
        CreateRoomRequestDto requestDto = RoomTestCaseFactory.createRoomRequestDto();

        //when
        CreateRoomResultDto resultDto = janusHelper.postAndGetResponseDto(requestDto, CreateRoomResultDto.class);

        //then
        Assertions.assertNotNull(resultDto);

        //destroy
        DestroyRoomRequestDto destroyRequestDto = RoomTestCaseFactory.destroyRoomRequestDto(resultDto.getResponse().getRoom());
        janusHelper.postAndGetResponseDto(destroyRequestDto, DestroyRoomResultDto.class);
    }

    @Test
    public void editPost() throws Exception {
        //given
        CreateRoomRequestDto createRequestDto = RoomTestCaseFactory.createRoomRequestDto();
        janusHelper.postAndGetResponseDto(createRequestDto, CreateRoomResultDto.class);
        EditRoomRequestDto editRequestDto = RoomTestCaseFactory.editRoomRequestDto();

        //when
        EditRoomResultDto editResultDto = janusHelper.postAndGetResponseDto(editRequestDto, EditRoomResultDto.class);

        //then
        Assertions.assertNotNull(editResultDto);
    }

    @Test
    public void DestroyPost() throws Exception {
        CreateRoomRequestDto createRequestDto = RoomTestCaseFactory.createRoomRequestDto();
        CreateRoomResultDto createResultDto = janusHelper.postAndGetResponseDto(createRequestDto, CreateRoomResultDto.class);
        DestroyRoomRequestDto destroyRequestDto = RoomTestCaseFactory.destroyRoomRequestDto(createResultDto.getResponse().getRoom());

        //when
        DestroyRoomResultDto destroyResultDto = janusHelper.postAndGetResponseDto(destroyRequestDto, DestroyRoomResultDto.class);

        //then
        Assertions.assertNotNull(destroyResultDto);
    }
}