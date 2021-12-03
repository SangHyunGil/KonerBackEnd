package project.SangHyun.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.advice.exception.NotResourceOwnerException;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.dto.MemberGetInfoResponseDto;
import project.SangHyun.domain.dto.MemberUpdateInfoResponseDto;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.ResponseService;
import project.SangHyun.domain.service.MemberService;
import project.SangHyun.web.dto.MemberUpdateInfoRequestDto;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ResponseService responseService;
    private final MemberService memberService;

    @ApiOperation(value = "유저 정보 로드", notes = "Access Token으로 유저에 대한 정보를 얻어온다.")
    @PostMapping("/info")
    public SingleResult<MemberGetInfoResponseDto> getMemberInfoByAccessToken(@AuthenticationPrincipal MemberDetails memberDetails) {
        return responseService.getSingleResult(memberService.getMemberInfo(memberDetails));
    }

    @ApiOperation(value = "유저 정보 로드", notes = "ID(PK)로 유저에 대한 정보를 얻어온다.")
    @GetMapping("/{userId}")
    public SingleResult<MemberGetInfoResponseDto> getMemberInfoByUserId(@PathVariable Long userId) {
        return responseService.getSingleResult(memberService.getMemberInfo(userId));
    }

    @ApiOperation(value = "유저 정보 수정", notes = "유저에 대한 정보를 수정한다.")
    @PutMapping("/{userId}")
    public SingleResult<MemberUpdateInfoResponseDto> updateMemberInfo(@AuthenticationPrincipal MemberDetails memberDetails,
                                                                      @PathVariable Long userId,
                                                                      @RequestBody MemberUpdateInfoRequestDto requestDto) {
        log.info("Compare = {}, {}", memberDetails.getUsername(), requestDto.getEmail());
        if (!memberDetails.getUsername().equals(requestDto.getEmail()))
            throw new NotResourceOwnerException();

        return responseService.getSingleResult(memberService.updateMemberInfo(userId, requestDto));
    }
}
