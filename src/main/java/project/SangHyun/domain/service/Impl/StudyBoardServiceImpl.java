package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.NotBelongStudyMemberException;
import project.SangHyun.advice.exception.StudyBoardNotFoundException;
import project.SangHyun.advice.exception.StudyHasNoProperRoleException;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.repository.StudyBoardRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.dto.request.study.StudyBoardCreateRequestDto;
import project.SangHyun.domain.service.StudyBoardService;
import project.SangHyun.dto.request.study.StudyBoardUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyBoardCreateResponseDto;
import project.SangHyun.dto.response.study.StudyBoardDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyBoardFindResponseDto;
import project.SangHyun.dto.response.study.StudyBoardUpdateResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardServiceImpl implements StudyBoardService {

    private final StudyBoardRepository studyBoardRepository;
    private final StudyJoinRepository studyJoinRepository;

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
    public StudyBoardUpdateResponseDto updateBoard(Long memberId, Long studyId, Long studyBoardId, StudyBoardUpdateRequestDto requestDto) {
        validateAuthority(memberId, studyId);
        StudyBoard studyBoard = studyBoardRepository.findById(studyBoardId).orElseThrow(StudyBoardNotFoundException::new);
        studyBoard.changeTitle(requestDto.getTitle());
        return StudyBoardUpdateResponseDto.createDto(studyBoard);
    }

    @Override
    public StudyBoardDeleteResponseDto deleteBoard(Long memberId, Long studyId, Long studyBoardId) {
        validateAuthority(memberId, studyId);
        StudyBoard studyBoard = studyBoardRepository.findById(studyBoardId).orElseThrow(StudyBoardNotFoundException::new);
        studyBoardRepository.delete(studyBoard);
        return StudyBoardDeleteResponseDto.createDto(studyBoard);
    }

    private void validateAuthority(Long memberId, Long studyId) {
        StudyJoin studyJoin = studyJoinRepository.findByMemberIdAndStudyId(memberId, studyId).orElseThrow(NotBelongStudyMemberException::new);
        if (!(studyJoin.getStudyRole().equals(StudyRole.CREATOR) || studyJoin.getStudyRole().equals(StudyRole.ADMIN)))
            throw new StudyHasNoProperRoleException();
    }
}
