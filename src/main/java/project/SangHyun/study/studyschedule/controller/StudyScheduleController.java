package project.SangHyun.study.studyschedule.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.study.studyschedule.controller.dto.request.StudyScheduleCreateRequestDto;
import project.SangHyun.study.studyschedule.controller.dto.request.StudyScheduleUpdateRequestDto;
import project.SangHyun.study.studyschedule.controller.dto.response.StudyScheduleResponseDto;
import project.SangHyun.study.studyschedule.service.StudyScheduleService;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies/{studyId}/schedules")
public class StudyScheduleController {

    private final StudyScheduleService studyScheduleService;
    private final ResponseService responseService;

    @ApiOperation(value = "스터디 스케줄 정보 로드", notes = "스터디 스케줄 정보를 모두 로드한다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<StudyScheduleResponseDto> findAllSchedules(@PathVariable Long studyId) {
        List<StudyScheduleDto> scheduleDto = studyScheduleService.findAllSchedules(studyId);
        List<StudyScheduleResponseDto> responseDto = responseService.convertToControllerDto(scheduleDto, StudyScheduleResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "스터디 스케줄 생성", notes = "스터디 스케줄을 생성한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResult<StudyScheduleResponseDto> createSchedule(@PathVariable Long studyId,
                                                                 @Valid @RequestBody StudyScheduleCreateRequestDto requestDto) {
        StudyScheduleDto scheduleDto = studyScheduleService.createSchedule(studyId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyScheduleResponseDto.create(scheduleDto));
    }

    @ApiOperation(value = "스터디 스케줄 세부 정보 로드", notes = "스터디 스케줄 세부 정보를 로드한다.")
    @GetMapping("/{studyScheduleId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<StudyScheduleResponseDto> findAllSchedules(@PathVariable Long studyId,
                                                                   @PathVariable Long studyScheduleId) {
        StudyScheduleDto scheduleDto = studyScheduleService.findSchedule(studyScheduleId);
        return responseService.getSingleResult(StudyScheduleResponseDto.create(scheduleDto));
    }

    @ApiOperation(value = "스터디 스케줄 업데이트", notes = "스터디 스케줄을 업데이트한다.")
    @PutMapping("/{studyScheduleId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<StudyScheduleResponseDto> updateSchedule(@PathVariable Long studyId,
                                                                 @PathVariable Long studyScheduleId,
                                                                 @Valid @RequestBody StudyScheduleUpdateRequestDto requestDto) {
        StudyScheduleDto scheduleDto = studyScheduleService.updateSchedule(studyScheduleId, requestDto.toServiceDto());
        return responseService.getSingleResult(StudyScheduleResponseDto.create(scheduleDto));
    }

    @ApiOperation(value = "스터디 스케줄 삭제", notes = "스터디 스케줄을 업데이트한다.")
    @DeleteMapping("/{studyScheduleId}")
    @ResponseStatus(HttpStatus.OK)
    public Result updateSchedule(@PathVariable Long studyId,
                                 @PathVariable Long studyScheduleId) {
        studyScheduleService.deleteSchedule(studyScheduleId);
        return responseService.getDefaultSuccessResult();
    }
}
