package project.SangHyun.domain.service;

import project.SangHyun.dto.response.chat.ChatFindResponseDto;
import project.SangHyun.dto.response.chat.ChatMessageResponseDto;
import project.SangHyun.dto.request.chat.ChatMessageRequestDto;

import java.util.List;

public interface ChatService {
    List<ChatFindResponseDto> findAllChats(Long roomId);
    ChatMessageResponseDto createChat(ChatMessageRequestDto requestDto);
}
