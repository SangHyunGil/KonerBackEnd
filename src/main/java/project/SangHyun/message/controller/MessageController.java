package project.SangHyun.message.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.dto.response.MultipleResult;
import project.SangHyun.dto.response.Result;
import project.SangHyun.dto.response.SingleResult;
import project.SangHyun.common.response.ResponseService;
import project.SangHyun.config.security.member.MemberDetails;
import project.SangHyun.message.controller.dto.request.MessageCreateRequestDto;
import project.SangHyun.message.controller.dto.response.FindCommunicatorsResponseDto;
import project.SangHyun.message.controller.dto.response.MessageResponseDto;
import project.SangHyun.message.service.MessageService;
import project.SangHyun.message.service.dto.response.FindCommunicatorsDto;
import project.SangHyun.message.service.dto.response.MessageDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final ResponseService responseService;

    @ApiOperation(value = "쪽지 상대 전체 조회", notes = "쪽지 상대 전체를 조회한다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<FindCommunicatorsResponseDto> findCommunicators(@AuthenticationPrincipal MemberDetails memberDetails) {
        List<FindCommunicatorsDto> findCommunicatorsDto = messageService.findAllCommunicatorsWithRecentMessage(memberDetails.getId());
        List<FindCommunicatorsResponseDto> responseDto = responseService.convertToControllerDto(findCommunicatorsDto, FindCommunicatorsResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "쪽지 상대 대화 내용 조회", notes = "쪽지 상대 대화 내용 조회한다.")
    @GetMapping("/sender")
    @ResponseStatus(HttpStatus.OK)
    public MultipleResult<MessageResponseDto> findMessages(@AuthenticationPrincipal MemberDetails memberDetails,
                                                           @RequestParam Long senderId) {
        List<MessageDto> messageDto = messageService.findAllMessages(senderId, memberDetails.getId());
        List<MessageResponseDto> responseDto = responseService.convertToControllerDto(messageDto, MessageResponseDto::create);
        return responseService.getMultipleResult(responseDto);
    }

    @ApiOperation(value = "읽지 않은 쪽지 개수 전체 조회", notes = "읽지 않은 쪽지 개수 전체 조회한다.")
    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public SingleResult<Long> countUnReadMessages(@AuthenticationPrincipal MemberDetails memberDetails) {
        return responseService.getSingleResult(messageService.countUnReadMessages(memberDetails.getId()));
    }

    @ApiOperation(value = "쪽지 전송", notes = "쪽지를 전송한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResult<MessageResponseDto> createMessage(@RequestBody MessageCreateRequestDto requestDto) {
        MessageDto messageDto = messageService.createMessage(requestDto.toServiceDto());
        return responseService.getSingleResult(MessageResponseDto.create(messageDto));
    }

    @ApiOperation(value = "전송자 쪽지 삭제", notes = "전송자가 쪽지를 삭제한다.")
    @DeleteMapping("/sender/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteMessageBySender(@PathVariable Long messageId) {
        messageService.deleteBySender(messageId);
        return responseService.getDefaultSuccessResult();
    }

    @ApiOperation(value = "수신자 쪽지 삭제", notes = "수신자가 쪽지를 삭제한다.")
    @DeleteMapping("/receiver/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteMessageByReceiver(@PathVariable Long messageId) {
        messageService.deleteByReceiver(messageId);
        return responseService.getDefaultSuccessResult();
    }
}
