package project.SangHyun.study.studyjoin.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinCreateRequestDto;
import project.SangHyun.study.studyjoin.controller.dto.request.StudyJoinUpdateAuthorityRequestDto;
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
@RequestMapping("/api/studies")
public class StudyJoinController {

    private final StudyJoinService studyJoinService;
    private final ResponseService responseService;

    @ApiOperation(value = "스터디원 정보 로드", notes = "스터디에 참여한 스터디원의 정보를 로드한다.")
    @GetMapping("/{studyId}/members")
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<StudyMembersResponseDto> findStudyMembers(@PathVariable Long studyId) {
        List<StudyMembersDto> studyMembersDto = studyJoinService.findStudyMembers(studyId);
        List<StudyMembersResponseDto> responseDto = responseService.convertToControllerDto(studyMembersDto, StudyMembersResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "참여 스터디 조회", notes = "참여한 스터디를 조회한다.")
    @GetMapping("/joins")
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<FindJoinedStudyResponseDto> findJoinedStudy(@ApiIgnore @AuthenticationPrincipal MemberDetails memberDetails) {
        List<FindJoinedStudyDto> findJoinedStudyDto = studyJoinService.findJoinedStudies(memberDetails.getId());
        List<FindJoinedStudyResponseDto> responseDto = responseService.convertToControllerDto(findJoinedStudyDto, FindJoinedStudyResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "스터디 참가 신청", notes = "스터디 참가를 신청한다.")
    @PostMapping("/{studyId}/joins/{memberId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Result applyJoin(@PathVariable Long studyId, @PathVariable Long memberId,
                            @RequestBody StudyJoinCreateRequestDto requestDto) {
        studyJoinService.applyJoin(studyId, memberId, requestDto.toServiceDto());
        return responseService.getDefaultSuccessResult();
    }

    @ApiOperation(value = "스터디 참가 수락", notes = "스터디 참가를 수락한다.")
    @PutMapping("/{studyId}/joins/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public Result acceptJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        studyJoinService.acceptJoin(studyId, memberId);
        return responseService.getDefaultSuccessResult();
    }

    @ApiOperation(value = "스터디 참가 거절", notes = "스터디 참가를 거절한다.")
    @DeleteMapping("/{studyId}/joins/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public Result rejectJoin(@PathVariable Long studyId, @PathVariable Long memberId) {
        studyJoinService.rejectJoin(studyId, memberId);
        return responseService.getDefaultSuccessResult();
    }

    @ApiOperation(value = "스터디 권한 수정", notes = "스터디 권한을 수정한다.")
    @PutMapping("/{studyId}/authorities/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public Result updateAuthority(@PathVariable Long studyId, @PathVariable Long memberId,
                                  @RequestBody StudyJoinUpdateAuthorityRequestDto requestDto) {
        studyJoinService.updateAuthority(studyId, memberId, requestDto.toServiceDto());
        return responseService.getDefaultSuccessResult();
    }
}