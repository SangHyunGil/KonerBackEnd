package project.SangHyun.message.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.common.response.domain.MultipleResult;
import project.SangHyun.common.response.domain.SingleResult;
import project.SangHyun.common.response.service.ResponseService;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.message.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.dto.response.CommunicatorFindResponseDto;
import project.SangHyun.message.dto.response.MessageCreateResponseDto;
import project.SangHyun.message.dto.response.MessageDeleteResponseDto;
import project.SangHyun.message.dto.response.MessageFindResponseDto;
import project.SangHyun.message.service.MessageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final ResponseService responseService;

    @ApiOperation(value = "쪽지 상대 전체 조회", notes = "쪽지 상대 전체를 조회한다.")
    @GetMapping
    public MultipleResult<CommunicatorFindResponseDto> findCommunicators(@AuthenticationPrincipal MemberDetails memberDetails) {
        return responseService.getMultipleResult(messageService.findAllCommunicatorsWithRecentMessage(memberDetails.getId()));
    }

    @ApiOperation(value = "쪽지 상대 대화 내용 조회", notes = "쪽지 상대 대화 내용 조회한다.")
    @GetMapping("/sender")
    public MultipleResult<MessageFindResponseDto> findMessages(@AuthenticationPrincipal MemberDetails memberDetails,
                                                               @RequestParam Long senderId) {
        return responseService.getMultipleResult(messageService.findAllMessages(senderId, memberDetails.getId()));
    }

    @ApiOperation(value = "읽지 않은 쪽지 개수 전체 조회", notes = "읽지 않은 쪽지 개수 전체 조회한다.")
    @GetMapping("/count")
    public SingleResult<Long> countUnReadMessages(@AuthenticationPrincipal MemberDetails memberDetails) {
        return responseService.getSingleResult(messageService.countUnReadMessages(memberDetails.getId()));
    }

    @ApiOperation(value = "쪽지 전송", notes = "쪽지를 전송한다.")
    @PostMapping
    public SingleResult<MessageCreateResponseDto> createMessage(@RequestBody MessageCreateRequestDto messageCreateRequestDto) {
        return responseService.getSingleResult(messageService.createMessage(messageCreateRequestDto));
    }

    @ApiOperation(value = "전송자 쪽지 삭제", notes = "전송자가 쪽지를 삭제한다.")
    @DeleteMapping("/sender/{messageId}")
    public SingleResult<MessageDeleteResponseDto> deleteMessageBySender(@PathVariable Long messageId) {
        return responseService.getSingleResult(messageService.deleteBySender(messageId));
    }

    @ApiOperation(value = "수신자 쪽지 삭제", notes = "수신자가 쪽지를 삭제한다.")
    @DeleteMapping("/receiver/{messageId}")
    public SingleResult<MessageDeleteResponseDto> deleteMessageByReceiver(@PathVariable Long messageId) {
        return responseService.getSingleResult(messageService.deleteByReceiver(messageId));
    }
}
