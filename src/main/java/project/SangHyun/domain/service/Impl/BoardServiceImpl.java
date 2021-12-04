package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.BoardNotFountException;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.advice.exception.MemberSameNickNameException;
import project.SangHyun.domain.entity.Board;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.BoardRepository;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.service.BoardService;
import project.SangHyun.dto.request.BoardCreateRequestDto;
import project.SangHyun.dto.request.BoardUpdateRequestDto;
import project.SangHyun.dto.response.BoardCreateResponseDto;
import project.SangHyun.dto.response.BoardFindResponseDto;
import project.SangHyun.dto.response.BoardUpdateResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final StudyJoinRepository studyJoinRepository;

    @Override
    @Transactional
    public BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
        Board board = boardRepository.save(Board.createBoard(requestDto, member));
        studyJoinRepository.save(StudyJoin.createStudyJoin(board, member));
        return BoardCreateResponseDto.createDto(board);
    }

    @Override
    public List<BoardFindResponseDto> findAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(board -> BoardFindResponseDto.createDto(board))
                .collect(Collectors.toList());
    }

    @Override
    public BoardFindResponseDto findBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(BoardNotFountException::new);
        return BoardFindResponseDto.createDto(board);
    }

    @Override
    @Transactional
    public BoardUpdateResponseDto updateBoardInfo(BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(BoardNotFountException::new);
        return BoardUpdateResponseDto.createDto(board.updateBoardInfo(requestDto));
    }
}
