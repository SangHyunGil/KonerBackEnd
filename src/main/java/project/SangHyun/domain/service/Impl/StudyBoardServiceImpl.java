package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.StudyBoardNotFoundException;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.repository.StudyBoardRepository;
import project.SangHyun.dto.request.StudyBoardCreateRequestDto;
import project.SangHyun.domain.service.StudyBoardService;
import project.SangHyun.dto.request.StudyBoardUpdateRequestDto;
import project.SangHyun.dto.response.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.StudyBoardFindResponseDto;
import project.SangHyun.dto.response.StudyBoardUpdateResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardServiceImpl implements StudyBoardService {

    private final StudyBoardRepository studyBoardRepository;

    @Override
    public List<StudyBoardFindResponseDto> findBoards(Long studyId) {
        List<StudyBoard> studyBoards = studyBoardRepository.findBoards(studyId);
        return studyBoards.stream()
                .map(studyBoard -> StudyBoardFindResponseDto.createDto(studyBoard))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudyBoardCreateResponseDto createBoard(Long studyId, StudyBoardCreateRequestDto requestDto) {
        StudyBoard studyBoard = studyBoardRepository.save(requestDto.toEntity(studyId));
        return StudyBoardCreateResponseDto.createDto(studyBoard);
    }

    @Override
    @Transactional
    public StudyBoardUpdateResponseDto updateBoard(Long studyId, Long studyBoardId, StudyBoardUpdateRequestDto requestDto) {
        StudyBoard studyBoard = studyBoardRepository.findById(studyBoardId).orElseThrow(StudyBoardNotFoundException::new);
        studyBoard.changeTitle(requestDto.getTitle());
        return StudyBoardUpdateResponseDto.createDto(studyBoard);
    }
}
