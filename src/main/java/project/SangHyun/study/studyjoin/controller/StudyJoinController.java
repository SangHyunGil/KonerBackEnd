package project.SangHyun.study.studyjoin.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.Result;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinCreateRequestDto;
import project.SangHyun.study.studyjoin.controller.dto.response.FindJoinedStudyResponseDto;
import project.SangHyun.study.studyjoin.controller.dto.response.StudyMembersResponseDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyjoin.service.dto.response.FindJoinedStudyDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyJoinController {

    private final StudyJoinService studyJoinService;
    private final ResponseService responseService;

    @ApiOperation(value = "스터디원 정보 로드", notes = "스터디에 참여한 스터디원의 정보를 로드한다.")
    @GetMapping("/{studyId}/member")
    public MultipleResult<StudyMembersResponseDto> findStudyMembers(@PathVariable Long studyId) {
        List<StudyMembersDto> studyMembersDto = studyJoinService.findStudyMembers(studyId);
        List<StudyMembersResponseDto> responseDto = responseService.convertToControllerDto(studyMembersDto, StudyMembersResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "참여 스터디 조회", notes = "참여한 스터디를 조회한다.")
    @GetMapping("/join")
    public MultipleResult<FindJoinedStudyResponseDto> findJoinedStudy(@ApiIgnore @AuthenticationPrincipal MemberDetails memberDetails) {
        List<FindJoinedStudyDto> findJoinedStudyDto = studyJoinService.findJoinedStudies(memberDetails.getId());
        List<FindJoinedStudyResponseDto> responseDto = responseService.convertToControllerDto(findJoinedStudyDto, FindJoinedStudyResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "스터디 참가 신청", notes = "스터디 참가를 신청한다.")
    @PostMapping("/{studyId}/join/{memberId}")
    public Result applyJoin(@PathVariable Long studyId, @PathVariable Long memberId,
                            @RequestBody StudyJoinCreateRequestDto requestDto) {
        studyJoinService.applyJoin(studyId, memberId, requestDto.toServiceDto());
        return responseService.getDefaultSuccessResult();
    }

    @ApiOperation(value = "스터디 참가 수락", notes = "스터디 참가를 수락한다.")
    @PutMapping("/{studyId}/join/{memberId}")
    public Result acceptJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        studyJoinService.acceptJoin(studyId, memberId);
        return responseService.getDefaultSuccessResult();
    }

    @ApiOperation(value = "스터디 참가 거절", notes = "스터디 참가를 거절한다.")
    @DeleteMapping("/{studyId}/join/{memberId}")
    public Result rejectJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        studyJoinService.rejectJoin(studyId, memberId);
        return responseService.getDefaultSuccessResult();
    }


}