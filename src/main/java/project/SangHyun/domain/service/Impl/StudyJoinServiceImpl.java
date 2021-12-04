package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.BoardNotFountException;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.domain.entity.Board;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.BoardRepository;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.service.StudyJoinService;
import project.SangHyun.dto.request.StudyJoinRequestDto;
import project.SangHyun.dto.response.StudyJoinCountResponseDto;
import project.SangHyun.dto.response.StudyJoinResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyJoinServiceImpl implements StudyJoinService {

    private final StudyJoinRepository studyJoinRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public StudyJoinResponseDto join(StudyJoinRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(BoardNotFountException::new);
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
        StudyJoin studyJoin = studyJoinRepository.save(StudyJoin.createStudyJoin(board, member));
        return StudyJoinResponseDto.createDto(studyJoin);
    }

    public StudyJoinCountResponseDto countStudyMember(Long boardId) {
        Long count = studyJoinRepository.findStudyMemberNum(boardId);
        return StudyJoinCountResponseDto.createDto(count);
    }
}
