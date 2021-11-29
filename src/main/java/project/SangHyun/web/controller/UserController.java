package project.SangHyun.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.domain.dto.MemberInfoResponseDto;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.ResponseService;
import project.SangHyun.domain.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ResponseService responseService;
    private final UserService userService;

    @ApiOperation(value = "유저 정보 로드", notes = "유저에 대한 정보를 얻어온다.")
    @PostMapping("/info")
    public SingleResult<MemberInfoResponseDto> getMemberInfoByAccessToken(@AuthenticationPrincipal MemberDetails memberDetails) {
        return responseService.getSingleResult(userService.getMemberInfo(memberDetails));
    }
}
