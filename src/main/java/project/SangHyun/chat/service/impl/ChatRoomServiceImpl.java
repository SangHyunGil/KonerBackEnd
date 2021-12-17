package project.SangHyun.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.chat.dto.response.ChatRoomCreateResponseDto;
import project.SangHyun.chat.dto.response.ChatRoomFindResponseDto;
import project.SangHyun.chat.entity.ChatRoom;
import project.SangHyun.chat.repository.ChatRoomRepository;
import project.SangHyun.chat.service.ChatRoomService;
import project.SangHyun.chat.dto.request.ChatRoomCreateRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoomCreateResponseDto createRoom(ChatRoomCreateRequestDto requestDto) {
        ChatRoom chatRoom = chatRoomRepository.save(requestDto.toEntity());
        return ChatRoomCreateResponseDto.create(chatRoom);
    }

    @Override
    public List<ChatRoomFindResponseDto> findAllRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream()
                .map(chatRoom -> ChatRoomFindResponseDto.create(chatRoom))
                .collect(Collectors.toList());
    }
}
