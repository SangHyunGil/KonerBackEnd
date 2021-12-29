package project.SangHyun.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.member.dto.request.MemberUpdateRequestDto;
import project.SangHyun.member.dto.response.MemberDeleteResponseDto;
import project.SangHyun.member.dto.response.MemberInfoResponseDto;
import project.SangHyun.member.dto.response.MemberProfileResponseDto;
import project.SangHyun.member.dto.response.MemberUpdateResponseDto;
import project.SangHyun.member.service.MemberService;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {
    private final ResponseServiceImpl responseService;
    private final MemberService memberService;

    @ApiOperation(value = "회원 정보 로드", notes = "Access Token으로 유저에 대한 정보를 얻어온다.")
    @PostMapping("/info")
    public SingleResult<MemberInfoResponseDto> getMemberInfo(@ApiIgnore @AuthenticationPrincipal MemberDetails memberDetails) {
        return responseService.getSingleResult(memberService.getMemberInfo(memberDetails));
    }

    @ApiOperation(value = "프로필 정보 로드", notes = "ID(PK)로 프로필에 대한 정보를 얻어온다.")
    @GetMapping("/{memberId}")
    public SingleResult<MemberProfileResponseDto> getProfile(@PathVariable Long memberId) {
        return responseService.getSingleResult(memberService.getProfile(memberId));
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원에 대한 정보를 수정한다.")
    @PutMapping("/{memberId}")
    public SingleResult<MemberUpdateResponseDto> updateMember(@PathVariable Long memberId, @Valid @ModelAttribute MemberUpdateRequestDto requestDto,
                                                              BindingResult bindingResult) throws IOException, BindException {
        if (bindingResult.hasErrors())
            throw new BindException(bindingResult);
        return responseService.getSingleResult(memberService.updateMember(memberId, requestDto));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제한다.")
    @DeleteMapping("/{memberId}")
    public SingleResult<MemberDeleteResponseDto> deleteMember(@PathVariable Long memberId) {
        return responseService.getSingleResult(memberService.deleteMember(memberId));
    }
}
