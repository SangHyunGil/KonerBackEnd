package project.SangHyun.chat.service;

import project.SangHyun.chat.dto.request.ChatRoomCreateRequestDto;
import project.SangHyun.chat.dto.response.ChatRoomCreateResponseDto;
import project.SangHyun.chat.dto.response.ChatRoomFindResponseDto;

import java.util.List;


public interface ChatRoomService {
    ChatRoomCreateResponseDto createRoom(ChatRoomCreateRequestDto requestDto);
    List<ChatRoomFindResponseDto> findAllRooms();
}
