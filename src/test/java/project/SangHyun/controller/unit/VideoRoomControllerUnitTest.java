package project.SangHyun.controller.unit;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.videoroom.controller.VideoRoomController;
import project.SangHyun.study.videoroom.controller.dto.request.VideoRoomCreateRequestDto;
import project.SangHyun.study.videoroom.controller.dto.request.VideoRoomUpdateRequestDto;
import project.SangHyun.study.videoroom.controller.dto.response.VideoRoomResponseDto;
import project.SangHyun.study.videoroom.domain.VideoRoom;
import project.SangHyun.study.videoroom.service.VideoRoomService;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomCreateDto;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomUpdateDto;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomDto;
import project.SangHyun.factory.videoroom.VideoRoomFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class VideoRoomControllerUnitTest {

    String accessToken;
    Member member;
    Study study;
    VideoRoom videoRoom;
    VideoRoom updatedVideoRoom;

    MockMvc mockMvc;
    @InjectMocks
    VideoRoomController videoRoomController;
    @Mock
    VideoRoomService videoRoomService;
    @Mock
    ResponseService responseService;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(videoRoomController).build();

        accessToken = "accessToken";
        member = VideoRoomFactory.makeTestAdminMember();
        study = VideoRoomFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        videoRoom = VideoRoomFactory.makeTestVideoRoom(1L, "????????? ????????????", member, study);
        updatedVideoRoom = VideoRoomFactory.makeTestVideoRoom(1L, "??????????????? ????????????", member, study);
    }

    @Test
    @DisplayName("???????????? ?????? ????????????.")
    public void createRoom() throws Exception {
        //given
        VideoRoomCreateRequestDto createRequestDto = VideoRoomFactory.createRequestDto(member.getId());
        VideoRoomCreateDto createDto = VideoRoomFactory.createDto(member.getId());
        VideoRoomDto videoRoomDto = VideoRoomFactory.makeDto(videoRoom);
        VideoRoomResponseDto responseDto = VideoRoomFactory.makeResponseDto(videoRoomDto);
        SingleResult<VideoRoomResponseDto> ExpectResult = VideoRoomFactory.makeSingleResult(responseDto);

        //mocking
        given(videoRoomService.createRoom(study.getId(), createDto)).willReturn(videoRoomDto);
        given(responseService.getSingleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(post("/api/studies/{studyId}/videorooms", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(createRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value("????????? ????????????"));
    }

    @Test
    @DisplayName("???????????? ?????? ????????????.")
    public void editRoom() throws Exception {
        //given
        VideoRoomUpdateRequestDto updateRequestDto = VideoRoomFactory.updateRequestDto("??????????????? ???????????? ???");
        VideoRoomUpdateDto updateDto = VideoRoomFactory.updateDto("??????????????? ???????????? ???");
        Result ExpectResult = VideoRoomFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(videoRoomService).updateRoom(videoRoom.getRoomId(), updateDto);
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(put("/api/studies/{studyId}/videorooms/{roomId}", study.getId(), videoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(updateRequestDto))
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("???????????? ?????? ????????????.")
    public void destroyRoom() throws Exception {
        //given
        Result ExpectResult = VideoRoomFactory.makeDefaultSuccessResult();

        //mocking
        willDoNothing().given(videoRoomService).deleteRoom(videoRoom.getRoomId());
        given(responseService.getDefaultSuccessResult()).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(delete("/api/studies/{studyId}/videorooms/{roomId}", study.getId(), videoRoom.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? ????????????.")
    public void findRooms() throws Exception {
        //given
        List<VideoRoomDto> videoRoomDto = List.of(VideoRoomFactory.makeDto(videoRoom));
        List<VideoRoomResponseDto> responseDto = videoRoomDto.stream()
                                                    .map(VideoRoomFactory::makeResponseDto)
                                                    .collect(Collectors.toList());
        MultipleResult<VideoRoomResponseDto> ExpectResult = VideoRoomFactory.makeMultipleResult(responseDto);

        //mocking
        given(videoRoomService.findRooms(study.getId())).willReturn(videoRoomDto);
        given(responseService.convertToControllerDto(any(List.class), any(Function.class))).willReturn(responseDto);
        given(responseService.getMultipleResult(responseDto)).willReturn(ExpectResult);

        //when, then
        mockMvc.perform(get("/api/studies/{studyId}/videorooms", study.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("X-AUTH-TOKEN", accessToken))
                .andExpect(status().isOk());
    }
}
