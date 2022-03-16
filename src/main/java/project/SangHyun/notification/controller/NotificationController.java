package project.SangHyun.notification.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.notification.controller.dto.response.NotificationResponseDto;
import project.SangHyun.notification.service.NotificationService;
import project.SangHyun.notification.service.dto.response.NotificationDto;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final ResponseService responseService;

    @ApiOperation(value = "알림 구독", notes = "알림을 구독한다.")
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe(@ApiIgnore @AuthenticationPrincipal MemberDetails memberDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(memberDetails.getId(), lastEventId);
    }

    @ApiOperation(value = "알림 조회", notes = "알림을 모두 조회한다.")
    @GetMapping(value = "/notifications")
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<NotificationResponseDto> findAllNotifications(@ApiIgnore @AuthenticationPrincipal MemberDetails memberDetails) {
        List<NotificationDto> notifications = notificationService.findAllNotifications(memberDetails.getId());
        List<NotificationResponseDto> responseDto = responseService.convertToControllerDto(notifications, NotificationResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "알림 조회", notes = "알림을 모두 조회한다.")
    @GetMapping(value = "/notifications/count")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<Long> countUnReadNotifications(@ApiIgnore @AuthenticationPrincipal MemberDetails memberDetails) {
        Long count = notificationService.countUnReadNotifications(memberDetails.getId());
        return responseService.getSingleResult(count);
    }
}