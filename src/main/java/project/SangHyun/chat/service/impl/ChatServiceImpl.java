package project.SangHyun.chat.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.SangHyun.advice.exception.ChatRoomNotFoundException;
import project.SangHyun.chat.dto.response.ChatFindResponseDto;
import project.SangHyun.chat.dto.response.ChatMessageResponseDto;
import project.SangHyun.chat.entity.Chat;
import project.SangHyun.chat.repository.ChatRepository;
import project.SangHyun.chat.service.ChatService;
import project.SangHyun.chat.dto.request.ChatMessageRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public List<ChatFindResponseDto> findAllChats(Long roomId) {
        List<Chat> chats = chatRepository.findAllByChatRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
        return chats.stream()
                .map(chat -> ChatFindResponseDto.create(chat))
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageResponseDto createChat(ChatMessageRequestDto requestDto) {
        Chat chat = chatRepository.save(requestDto.toEntity());
        return ChatMessageResponseDto.create(chat);
    }
}
