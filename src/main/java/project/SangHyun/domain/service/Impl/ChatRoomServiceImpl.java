package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.dto.response.chat.ChatRoomCreateResponseDto;
import project.SangHyun.dto.response.chat.ChatRoomFindResponseDto;
import project.SangHyun.domain.entity.ChatRoom;
import project.SangHyun.domain.repository.ChatRoomRepository;
import project.SangHyun.domain.service.ChatRoomService;
import project.SangHyun.dto.request.chat.ChatRoomCreateRequestDto;

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
        return ChatRoomCreateResponseDto.createDto(chatRoom);
    }

    @Override
    public List<ChatRoomFindResponseDto> findAllRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream()
                .map(chatRoom -> ChatRoomFindResponseDto.createDto(chatRoom))
                .collect(Collectors.toList());
    }
}
