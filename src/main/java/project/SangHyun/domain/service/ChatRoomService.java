package project.SangHyun.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.domain.dto.ChatRoomCreateResponseDto;
import project.SangHyun.domain.dto.ChatRoomFindResponseDto;
import project.SangHyun.domain.entity.ChatRoom;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.repository.ChatRoomRepository;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.web.dto.ChatRoomCreateRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public ChatRoomCreateResponseDto createRoom(ChatRoomCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.createRoom(requestDto.getRoomName(), member));
        return ChatRoomCreateResponseDto.createDto(chatRoom);
    }

    public List<ChatRoomFindResponseDto> findAllRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream()
                .map(chatRoom -> ChatRoomFindResponseDto.createDto(chatRoom))
                .collect(Collectors.toList());
    }
}
