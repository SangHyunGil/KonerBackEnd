package project.SangHyun.study.studyjoin.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.FindJoinedStudyResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyJoinController {
    private final StudyJoinService studyJoinService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "스터디원 정보 로드", notes = "스터디에 참여한 스터디원의 정보를 로드한다.")
    @GetMapping("/{studyId}/member")
    public MultipleResult<StudyFindMembersResponseDto> findStudyMembers(@PathVariable Long studyId) {
        return responseService.getMultipleResult(studyJoinService.findStudyMembers(studyId));
    }

    @ApiOperation(value = "참여 스터디 조회", notes = "참여한 스터디를 조회한다.")
    @GetMapping("/join")
    public MultipleResult<FindJoinedStudyResponseDto> findJoinedStudy(@ApiIgnore @AuthenticationPrincipal MemberDetails memberDetails) {
        return responseService.getMultipleResult(studyJoinService.findJoinedStudies(memberDetails.getId()));
    }

    @ApiOperation(value = "스터디 참가 신청", notes = "스터디 참가를 신청한다.")
    @PostMapping("/{studyId}/join/{memberId}")
    public SingleResult<StudyJoinResponseDto> applyJoin(@PathVariable Long studyId, @PathVariable Long memberId,
                                                        @RequestBody StudyJoinRequestDto requestDto) {
        return responseService.getSingleResult(studyJoinService.applyJoin(studyId, memberId, requestDto));
    }

    @ApiOperation(value = "스터디 참가 수락", notes = "스터디 참가를 수락한다.")
    @PutMapping("/{studyId}/join/{memberId}")
    public SingleResult<StudyJoinResponseDto> acceptJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        return responseService.getSingleResult(studyJoinService.acceptJoin(studyId, memberId));
    }

    @ApiOperation(value = "스터디 참가 거절", notes = "스터디 참가를 거절한다.")
    @DeleteMapping("/{studyId}/join/{memberId}")
    public SingleResult<StudyJoinResponseDto> rejectJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        return responseService.getSingleResult(studyJoinService.rejectJoin(studyId, memberId));
    }
}