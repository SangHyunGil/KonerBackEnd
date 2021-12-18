package project.SangHyun.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.SangHyun.member.dto.request.*;
import project.SangHyun.member.dto.response.MemberChangePwResponseDto;
import project.SangHyun.member.dto.response.MemberLoginResponseDto;
import project.SangHyun.member.dto.response.MemberRegisterResponseDto;
import project.SangHyun.member.dto.response.TokenResponseDto;
import project.SangHyun.member.service.SignService;
import project.SangHyun.response.domain.SingleResult;
import project.SangHyun.response.service.ResponseServiceImpl;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 진행한다.")
    @PostMapping("/register")
    public SingleResult<MemberRegisterResponseDto> register(@Valid @RequestBody MemberRegisterRequestDto requestDto) {
        MemberRegisterResponseDto responseDto = signService.registerMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "검증 메일 발송", notes = "검증을 위해 메일을 발송한다.")
    @PostMapping("/email")
    public SingleResult<String> sendEmail(@RequestBody MemberEmailAuthRequestDto requestDto) {
        String result = signService.sendEmail(requestDto);
        return responseService.getSingleResult(result);
    }

    @ApiOperation(value = "이메일 인증", notes = "이메일 인증을 진행한다.")
    @PostMapping("/verify")
    public SingleResult<String> verify(@RequestBody VerifyEmailRequestDto requestDto) {
        String result = signService.verify(requestDto);
        return responseService.getSingleResult(result);
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호 변경을 진행한다.")
    @PostMapping("/password")
    public SingleResult<MemberChangePwResponseDto> changePassword(@Valid @RequestBody MemberChangePwRequestDto requestDto) {
        MemberChangePwResponseDto responseDto = signService.changePassword(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "로컬 로그인", notes = "로컬을 통해 로그인을 진행한다.")
    @PostMapping("/login")
    public SingleResult<MemberLoginResponseDto> login(@Valid @RequestBody MemberLoginRequestDto requestDto) {
        MemberLoginResponseDto responseDto = signService.loginMember(requestDto);
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "토큰 재발급", notes = "Refresh Token을 통해 토큰을 재발급받는다.")
    @PostMapping("/reissue")
    public SingleResult<TokenResponseDto> reIssue(@RequestBody TokenRequestDto requestDto) {
        TokenResponseDto responseDto = signService.tokenReIssue(requestDto);
        return responseService.getSingleResult(responseDto);
    }
}
