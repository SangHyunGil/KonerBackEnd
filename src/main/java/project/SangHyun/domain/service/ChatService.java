package project.SangHyun.domain.service;

import project.SangHyun.dto.response.ChatFindResponseDto;
import project.SangHyun.dto.response.ChatMessageResponseDto;
import project.SangHyun.dto.request.ChatMessageRequestDto;

import java.util.List;

public interface ChatService {
    List<ChatFindResponseDto> findAllChats(Long roomId);
    ChatMessageResponseDto createChat(ChatMessageRequestDto requestDto);
}
