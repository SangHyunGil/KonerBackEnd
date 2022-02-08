package project.SangHyun.member.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseServiceImpl;
import project.SangHyun.member.dto.request.MemberEmailAuthRequestDto;
import project.SangHyun.member.service.EmailService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class EmailController {

    private final EmailService emailService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "검증 메일 발송", notes = "검증을 위해 메일을 발송한다.")
    @PostMapping("/email")
    public SingleResult<String> sendEmail(@RequestBody MemberEmailAuthRequestDto requestDto) {
        return responseService.getSingleResult(emailService.send(requestDto.getEmail(), requestDto.getRedisKey()));
    }
}
