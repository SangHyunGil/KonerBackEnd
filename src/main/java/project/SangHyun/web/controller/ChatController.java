package project.SangHyun.web.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import project.SangHyun.dto.response.ChatFindResponseDto;
import project.SangHyun.dto.response.ChatMessageResponseDto;
import project.SangHyun.dto.response.ChatRoomCreateResponseDto;
import project.SangHyun.dto.response.ChatRoomFindResponseDto;
import project.SangHyun.domain.response.MultipleResult;
import project.SangHyun.domain.response.SingleResult;
import project.SangHyun.domain.service.ChatRoomService;
import project.SangHyun.domain.service.ChatService;
import project.SangHyun.domain.service.Impl.ResponseServiceImpl;
import project.SangHyun.dto.request.ChatMessageRequestDto;
import project.SangHyun.dto.request.ChatRoomCreateRequestDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final ResponseServiceImpl responseService;

    @ApiOperation(value = "Pub, Sub", notes = "Stomp의 메세지를 처리한다.")
    @MessageMapping("/chat/{roomId}")
    @SendTo("/sub/{roomId}")
    public ChatMessageResponseDto sendChat(ChatMessageRequestDto message) {
        return chatService.createChat(message);
    }

    @ApiOperation(value = "모든 채팅방 조회", notes = "모든 채팅방을 조회한다.")
    @GetMapping("/room")
    public MultipleResult<ChatRoomFindResponseDto> findAllRooms() {
        return responseService.getMultipleResult(chatRoomService.findAllRooms());
    }

    @ApiOperation(value = "채팅방 생성", notes = "채팅방을 생성한다.")
    @PostMapping("/room")
    public SingleResult<ChatRoomCreateResponseDto> createRoom(@RequestBody ChatRoomCreateRequestDto requestDto) {
        ChatRoomCreateResponseDto room = chatRoomService.createRoom(requestDto);
        return responseService.getSingleResult(room);
    }

    @ApiOperation(value = "모든 채팅 조회", notes = "모든 채팅을 조회한다.")
    @GetMapping("/room/{roomId}")
    public MultipleResult<ChatFindResponseDto> findAllChats(@PathVariable Long roomId) {
        return responseService.getMultipleResult(chatService.findAllChats(roomId));
    }
}
