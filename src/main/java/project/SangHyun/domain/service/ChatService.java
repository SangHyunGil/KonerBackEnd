package project.SangHyun.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.SangHyun.advice.exception.ChatRoomNotFoundException;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.domain.dto.ChatFindResponseDto;
import project.SangHyun.domain.dto.ChatMessageResponseDto;
import project.SangHyun.domain.entity.Chat;
import project.SangHyun.domain.entity.ChatRoom;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.repository.ChatRepository;
import project.SangHyun.domain.repository.ChatRoomRepository;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.web.dto.ChatMessageRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public List<ChatFindResponseDto> findAllChats(Long roomId) {
        List<Chat> chats = chatRepository.findAllByChatRoomId(roomId).orElseThrow(ChatRoomNotFoundException::new);
        return chats.stream()
                .map(chat -> ChatFindResponseDto.createDto(chat))
                .collect(Collectors.toList());
    }

    public ChatMessageResponseDto createChat(ChatMessageRequestDto requestDto) {
        log.info("memberId = {}", requestDto.getMemberId());
        ChatRoom chatRoom = roomRepository.findById(requestDto.getRoomId()).orElseThrow(ChatRoomNotFoundException::new);
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Chat chat = chatRepository.save(Chat.createChat(requestDto.getContent(), chatRoom, member));
        return ChatMessageResponseDto.createDto(chat);
    }
}
