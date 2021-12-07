package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.SangHyun.advice.exception.ChatRoomNotFoundException;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.dto.response.ChatFindResponseDto;
import project.SangHyun.dto.response.ChatMessageResponseDto;
import project.SangHyun.domain.entity.Chat;
import project.SangHyun.domain.entity.ChatRoom;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.repository.ChatRepository;
import project.SangHyun.domain.repository.ChatRoomRepository;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.service.ChatService;
import project.SangHyun.dto.request.ChatMessageRequestDto;

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
                .map(chat -> ChatFindResponseDto.createDto(chat))
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageResponseDto createChat(ChatMessageRequestDto requestDto) {
        Chat chat = chatRepository.save(requestDto.toEntity());
        return ChatMessageResponseDto.createDto(chat);
    }
}
