package project.SangHyun.study.study.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.StudyCreateResponseDto;
import project.SangHyun.study.study.dto.response.StudyDeleteResponseDto;
import project.SangHyun.study.study.dto.response.StudyFindResponseDto;
import project.SangHyun.study.study.dto.response.StudyUpdateResponseDto;
import project.SangHyun.study.study.service.StudyService;

import javax.validation.Valid;
import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    private final StudyService studyService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "스터디 정보 로드", notes = "모든 스터디 정보를 얻어온다.")
    @GetMapping
    public MultipleResult<StudyFindResponseDto> findAllStudies() {
        return responseService.getMultipleResult(studyService.findAllStudies());
    }

    @ApiOperation(value = "스터디 세부 정보 로드", notes = "스터디 세부 정보를 얻어온다.")
    @GetMapping("/{studyId}")
    public SingleResult<StudyFindResponseDto> findStudy(@PathVariable Long studyId) {
        return responseService.getSingleResult(studyService.findStudy(studyId));
    }

    @ApiOperation(value = "스터디 생성", notes = "스터디를 생성한다.")
    @PostMapping
    public SingleResult<StudyCreateResponseDto> createStudy(@Valid @ModelAttribute StudyCreateRequestDto requestDto) throws IOException {
        return responseService.getSingleResult(studyService.createStudy(requestDto));
    }

    @ApiOperation(value = "스터디 정보 업데이트", notes = "스터디 정보를 업데이트한다.")
    @PutMapping("/{studyId}")
    public SingleResult<StudyUpdateResponseDto> updateStudy(@PathVariable Long studyId,
                                                            @Valid @ModelAttribute StudyUpdateRequestDto requestDto) throws IOException {
        return responseService.getSingleResult(studyService.updateStudy(studyId, requestDto));
    }

    @ApiOperation(value = "스터디 정보 삭제", notes = "스터디 정보를 삭제한다.")
    @DeleteMapping("/{studyId}")
    public SingleResult<StudyDeleteResponseDto> deleteStudy(@PathVariable Long studyId) {
        return responseService.getSingleResult(studyService.deleteStudy(studyId));
    }
}
