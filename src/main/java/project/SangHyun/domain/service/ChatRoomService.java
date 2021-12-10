package project.SangHyun.domain.service;

import project.SangHyun.dto.response.chat.ChatRoomCreateResponseDto;
import project.SangHyun.dto.response.chat.ChatRoomFindResponseDto;
import project.SangHyun.dto.request.chat.ChatRoomCreateRequestDto;

import java.util.List;


public interface ChatRoomService {
    ChatRoomCreateResponseDto createRoom(ChatRoomCreateRequestDto requestDto);
    List<ChatRoomFindResponseDto> findAllRooms();
}
