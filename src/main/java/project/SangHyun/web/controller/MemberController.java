package project.SangHyun.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.advice.exception.NotResourceOwnerException;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.dto.response.member.MemberDeleteResponseDto;
import project.SangHyun.dto.response.member.MemberInfoResponseDto;
import project.SangHyun.dto.response.member.MemberProfileResponseDto;
import project.SangHyun.dto.response.member.MemberUpdateInfoResponseDto;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.MemberService;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.dto.request.member.MemberUpdateInfoRequestDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final ResponseServiceImpl responseService;
    private final MemberService memberService;

    @ApiOperation(value = "회원 정보 로드", notes = "Access Token으로 유저에 대한 정보를 얻어온다.")
    @PostMapping("/info")
    public SingleResult<MemberInfoResponseDto> getMemberInfo(@AuthenticationPrincipal MemberDetails memberDetails) {
        return responseService.getSingleResult(memberService.getMemberInfo(memberDetails));
    }

    @ApiOperation(value = "프로필 정보 로드", notes = "ID(PK)로 프로필에 대한 정보를 얻어온다.")
    @GetMapping("/{memberId}")
    public SingleResult<MemberProfileResponseDto> getProfile(@PathVariable Long memberId) {
        return responseService.getSingleResult(memberService.getProfile(memberId));
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원에 대한 정보를 수정한다.")
    @PutMapping("/{memberId}")
    public SingleResult<MemberUpdateInfoResponseDto> updateMemberInfo(@PathVariable Long memberId,
                                                                      @Valid @RequestBody MemberUpdateInfoRequestDto requestDto) {
        return responseService.getSingleResult(memberService.updateMemberInfo(memberId, requestDto));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제한다.")
    @DeleteMapping("/{memberId}")
    public SingleResult<MemberDeleteResponseDto> deleteMember(@PathVariable Long memberId) {
        return responseService.getSingleResult(memberService.deleteMember(memberId));
    }
}
