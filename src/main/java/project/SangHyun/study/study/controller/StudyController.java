package project.SangHyun.study.study.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.dto.response.SliceResponseDto;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.study.study.controller.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.controller.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.controller.dto.response.StudyResponseDto;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.study.study.service.dto.response.StudyDto;

import javax.validation.Valid;
import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studies")
public class StudyController {

    private final StudyService studyService;
    private final ResponseService responseService;

    @ApiOperation(value = "스터디 정보 로드", notes = "모든 스터디 정보를 얻어온다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<SliceResponseDto> findAllStudies(@RequestParam Long studyId, @RequestParam StudyCategory department,
                                                         @RequestParam Integer size) {
        return responseService.getSingleResult(studyService.findAllStudiesByDepartment(studyId, department, size));
    }

    @ApiOperation(value = "스터디 세부 정보 로드", notes = "스터디 세부 정보를 얻어온다.")
    @GetMapping("/{studyId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<StudyResponseDto> findStudy(@PathVariable Long studyId) {
        StudyDto studyDto = studyService.findStudy(studyId);
        return responseService.getSingleResult(StudyResponseDto.create(studyDto));
    }

    @ApiOperation(value = "스터디 생성", notes = "스터디를 생성한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResult<StudyResponseDto> createStudy(@Valid @ModelAttribute StudyCreateRequestDto requestDto) throws IOException {
        StudyDto studyDto = studyService.createStudy(requestDto.toServiceDto());
        return responseService.getSingleResult(StudyResponseDto.create(studyDto));
    }

    @ApiOperation(value = "스터디 정보 업데이트", notes = "스터디 정보를 업데이트한다.")
    @PutMapping("/{studyId}")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<StudyResponseDto> updateStudy(@PathVariable Long studyId,
                                                            @Valid @ModelAttribute StudyUpdateRequestDto requestDto) throws IOException {
        StudyDto studyDto = studyService.updateStudy(studyId, requestDto.toServiceDTO());
        return responseService.getSingleResult(StudyResponseDto.create(studyDto));
    }

    @ApiOperation(value = "스터디 정보 삭제", notes = "스터디 정보를 삭제한다.")
    @DeleteMapping("/{studyId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteStudy(@PathVariable Long studyId) {
        studyService.deleteStudy(studyId);
        return responseService.getDefaultSuccessResult();
    }
}
