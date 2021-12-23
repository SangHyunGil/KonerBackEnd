package project.SangHyun.study.studyjoin.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyJoinController {
    private final StudyJoinService studyJoinService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "스터디 참여", notes = "스터디에 참여한다.")
    @PostMapping("/join")
    public SingleResult<StudyJoinResponseDto> join(@RequestBody StudyJoinRequestDto requestDto) {
        return responseService.getSingleResult(studyJoinService.joinStudy(requestDto));
    }

    @ApiOperation(value = "스터디원 정보 로드", notes = "스터디에 참여한 스터디원의 정보를 로드한다.")
    @GetMapping("/{studyId}/member")
    public MultipleResult<StudyFindMembersResponseDto> findStudyMembers(@PathVariable Long studyId) {
        return responseService.getMultipleResult(studyJoinService.findStudyMembers(studyId));
    }
}
