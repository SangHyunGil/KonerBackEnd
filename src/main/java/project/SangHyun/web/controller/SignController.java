package project.SangHyun.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.domain.dto.*;
import project.SangHyun.domain.result.SingleResult;
import project.SangHyun.domain.service.ResponseService;
import project.SangHyun.domain.service.SignService;
import project.SangHyun.web.dto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;
    private final ResponseService responseService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 진행한다.")
    @PostMapping("/register")
    public SingleResult<MemberRegisterResponseDto> register(@RequestBody MemberRegisterRequestDto requestDto) {
        log.info("request = {}, {}", requestDto.getEmail(), requestDto.getPassword());
        MemberRegisterResponseDto responseDto = signService.registerMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "검증 메일 발송", notes = "검증을 위해 메일을 발송한다.")
    @PostMapping("/email")
    public SingleResult<String> sendEmail(@RequestBody MemberEmailAuthRequestDto requestDto) {
        String result = signService.sendEmail(requestDto);
        return responseService.getSingleResult(result);
    }

    @ApiOperation(value = "메일 링크 검증", notes = "메일 링크를 검증한다.")
    @GetMapping("/email")
    public SingleResult<ValidateLinkResponseDto> validateLink(@ModelAttribute ValidateLinkRequestDto requestDto) {
        ValidateLinkResponseDto responseDto = signService.validateLink(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "이메일 인증", notes = "이메일 인증을 진행한다.")
    @PostMapping("/verify")
    public SingleResult<String> verifyEmail(@RequestBody VerifyEmailRequestDto requestDto) {
        String result = signService.verifyEmail(requestDto);
        return responseService.getSingleResult(result);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호 변경을 진행한다.")
    @PostMapping("/password")
    public SingleResult<MemberChangePwResponseDto> changePassword(@RequestBody MemberChangePwRequestDto requestDto) {
        MemberChangePwResponseDto responseDto = signService.changePassword(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "로컬 로그인", notes = "로컬을 통해 로그인을 진행한다.")
    @PostMapping("/login")
    public SingleResult<MemberLoginResponseDto> login(@RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "토큰 재발급", notes = "Refresh Token을 통해 토큰을 재발급받는다.")
    @PostMapping("/reissue")
    public SingleResult<TokenResponseDto> reIssue(@RequestBody ReIssueRequestDto requestDto) {
        TokenResponseDto responseDto = signService.reIssue(requestDto);
        return responseService.getSingleResult(responseDto);
    }
}
