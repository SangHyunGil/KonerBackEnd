package project.SangHyun.study.studyjoin.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.response.domain.MultipleResult;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study/{studyId}")
public class StudyJoinController {
    private final StudyJoinService studyJoinService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "스터디원 정보 로드", notes = "스터디에 참여한 스터디원의 정보를 로드한다.")
    @GetMapping("/member")
    public MultipleResult<StudyFindMembersResponseDto> findStudyMembers(@PathVariable Long studyId) {
        return responseService.getMultipleResult(studyJoinService.findStudyMembers(studyId));
    }

    @ApiOperation(value = "스터디 참가 신청", notes = "스터디 참가를 신청한다.")
    @PostMapping("/join/{memberId}")
    public SingleResult<StudyJoinResponseDto> applyJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        return responseService.getSingleResult(studyJoinService.applyJoin(studyId, memberId));
    }

    @ApiOperation(value = "스터디 참가 수락", notes = "스터디 참가를 수락한다.")
    @PutMapping("/join/{memberId}")
    public SingleResult<StudyJoinResponseDto> acceptJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        return responseService.getSingleResult(studyJoinService.acceptJoin(studyId, memberId));
    }

    @ApiOperation(value = "스터디 참가 거절", notes = "스터디 참가를 거절한다.")
    @DeleteMapping("/join/{memberId}")
    public SingleResult<StudyJoinResponseDto> rejectJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        return responseService.getSingleResult(studyJoinService.rejectJoin(studyId, memberId));
    }
}