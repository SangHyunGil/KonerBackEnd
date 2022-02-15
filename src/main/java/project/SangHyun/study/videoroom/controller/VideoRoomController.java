package project.SangHyun.study.videoroom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.study.videoroom.controller.dto.request.VideoRoomCreateRequestDto;
import project.SangHyun.study.videoroom.controller.dto.request.VideoRoomUpdateRequestDto;
import project.SangHyun.study.videoroom.controller.dto.response.VideoRoomResponseDto;
import project.SangHyun.study.videoroom.service.VideoRoomService;
import project.SangHyun.study.videoroom.service.dto.response.VideoRoomDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}/videoroom")
public class VideoRoomController {

    private final VideoRoomService videoRoomService;
    private final ResponseService responseService;

    @GetMapping
    public MultipleResult<VideoRoomResponseDto> findAllRooms(@PathVariable Long studyId) {
        List<VideoRoomDto> videoRoomDto = videoRoomService.findRooms(studyId);
        List<VideoRoomResponseDto> responseDto = responseService.convertToControllerDto(videoRoomDto, VideoRoomResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @PostMapping
    public SingleResult<VideoRoomResponseDto> createRoom(@PathVariable Long studyId,
                                                         @Valid @RequestBody VideoRoomCreateRequestDto requestDto) {
        VideoRoomDto videoRoomDto = videoRoomService.createRoom(studyId, requestDto.toServiceDto());
        return responseService.getSingleResult(VideoRoomResponseDto.create(videoRoomDto));
    }

    @PutMapping("/{videoRoomId}")
    public Result updateRoom(@PathVariable Long studyId, @PathVariable Long videoRoomId,
                             @Valid @RequestBody VideoRoomUpdateRequestDto requestDto) {
        videoRoomService.updateRoom(videoRoomId, requestDto.toServiceDto());
        return responseService.getDefaultSuccessResult();
    }

    @DeleteMapping("/{videoRoomId}")
    public Result deleteRoom(@PathVariable Long studyId, @PathVariable Long videoRoomId) {
        videoRoomService.deleteRoom(videoRoomId);
        return responseService.getDefaultSuccessResult();
    }
}
