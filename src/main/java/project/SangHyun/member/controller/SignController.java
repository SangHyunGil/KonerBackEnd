package project.SangHyun.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.member.controller.dto.request.LoginRequestDto;
import project.SangHyun.member.controller.dto.request.MemberRegisterRequestDto;
import project.SangHyun.member.controller.dto.request.TokenRequestDto;
import project.SangHyun.member.controller.dto.response.LoginResponseDto;
import project.SangHyun.member.controller.dto.response.MemberResponseDto;
import project.SangHyun.member.controller.dto.response.TokenResponseDto;
import project.SangHyun.member.service.SignService;
import project.SangHyun.member.service.dto.response.MemberDto;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 진행한다.")
    @PostMapping("/register")
    public SingleResult<MemberResponseDto> register(@Valid @ModelAttribute MemberRegisterRequestDto requestDto,
                                                    BindingResult bindingResult) throws IOException, BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        MemberDto memberDto = signService.registerMember(requestDto.toServiceDto());
        return responseService.getSingleResult(MemberResponseDto.create(memberDto));
    }

    @ApiOperation(value = "로컬 로그인", notes = "로컬을 통해 로그인을 진행한다.")
    @PostMapping("/login")
    public SingleResult<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return responseService.getSingleResult(signService.loginMember(requestDto));
    }

    @ApiOperation(value = "토큰 재발급", notes = "Refresh Token을 통해 토큰을 재발급받는다.")
    @PostMapping("/reissue")
    public SingleResult<TokenResponseDto> reIssue(@RequestBody TokenRequestDto requestDto) {
        return responseService.getSingleResult(signService.tokenReIssue(requestDto));
    }
}
