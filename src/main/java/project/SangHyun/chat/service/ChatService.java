package project.SangHyun.chat.service;

import project.SangHyun.chat.dto.response.ChatFindResponseDto;
import project.SangHyun.chat.dto.response.ChatMessageResponseDto;
import project.SangHyun.chat.dto.request.ChatMessageRequestDto;

import java.util.List;

public interface ChatService {
    List<ChatFindResponseDto> findAllChats(Long roomId);
    ChatMessageResponseDto createChat(ChatMessageRequestDto requestDto);
}
