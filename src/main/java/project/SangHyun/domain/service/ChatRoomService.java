package project.SangHyun.domain.service;

import project.SangHyun.dto.response.ChatRoomCreateResponseDto;
import project.SangHyun.dto.response.ChatRoomFindResponseDto;
import project.SangHyun.dto.request.ChatRoomCreateRequestDto;

import java.util.List;


public interface ChatRoomService {
    ChatRoomCreateResponseDto createRoom(ChatRoomCreateRequestDto requestDto);
    List<ChatRoomFindResponseDto> findAllRooms();
}
