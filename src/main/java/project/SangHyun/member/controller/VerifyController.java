package project.SangHyun.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.member.controller.dto.request.EmailAuthRequestDto;
import project.SangHyun.member.controller.dto.request.VerifyRequestDto;
import project.SangHyun.member.service.VerifyService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class VerifyController {

    private final VerifyService verifyService;
    private final ResponseService responseService;

    @ApiOperation(value = "검증 메일 발송", notes = "검증을 위해 메일을 발송한다.")
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<String> sendEmail(@RequestBody EmailAuthRequestDto requestDto) {
        return responseService.getSingleResult(verifyService.send(requestDto.getEmail(), requestDto.getRedisKey()));
    }

    @ApiOperation(value = "검증 메일 인증", notes = "검증 메일으로 발송된 인증번호를 검증한다.")
    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<String> verify(@RequestBody VerifyRequestDto requestDto) {
        return responseService.getSingleResult(verifyService.verify(requestDto));
    }
}
