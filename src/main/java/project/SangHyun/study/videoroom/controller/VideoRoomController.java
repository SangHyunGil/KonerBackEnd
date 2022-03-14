package project.SangHyun.study.videoroom.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
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

    @ApiOperation(value = "스터디 화상회의 방 로드", notes = "스터디에 생성된 화상회의 방을 모두 로드한다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<VideoRoomResponseDto> findAllRooms(@PathVariable Long studyId) {
        List<VideoRoomDto> videoRoomDto = videoRoomService.findRooms(studyId);
        List<VideoRoomResponseDto> responseDto = responseService.convertToControllerDto(videoRoomDto, VideoRoomResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "스터디 화상회의 방 생성", notes = "스터디에 화상회의 방을 생성한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResult<VideoRoomResponseDto> createRoom(@PathVariable Long studyId,
                                                         @Valid @RequestBody VideoRoomCreateRequestDto requestDto) {
        VideoRoomDto videoRoomDto = videoRoomService.createRoom(studyId, requestDto.toServiceDto());
        return responseService.getSingleResult(VideoRoomResponseDto.create(videoRoomDto));
    }

    @ApiOperation(value = "스터디 화상회의 방 수정", notes = "스터디에 생성된 화상회의 방을 수정한다.")
    @PutMapping("/{videoRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public Result updateRoom(@PathVariable Long studyId, @PathVariable Long videoRoomId,
                             @Valid @RequestBody VideoRoomUpdateRequestDto requestDto) {
        videoRoomService.updateRoom(videoRoomId, requestDto.toServiceDto());
        return responseService.getDefaultSuccessResult();
    }

    @ApiOperation(value = "스터디 화상회의 방 삭제", notes = "스터디에 생성된 화상회의 방을 삭제한다.")
    @DeleteMapping("/{videoRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteRoom(@PathVariable Long studyId, @PathVariable Long videoRoomId) {
        videoRoomService.deleteRoom(videoRoomId);
        return responseService.getDefaultSuccessResult();
    }
}
