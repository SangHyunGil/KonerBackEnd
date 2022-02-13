package project.SangHyun.study.studyboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.StudyBoardNotFoundException;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.repository.StudyBoardRepository;
import project.SangHyun.study.studyboard.service.dto.request.StudyBoardCreateDto;
import project.SangHyun.study.studyboard.service.dto.request.StudyBoardUpdateDto;
import project.SangHyun.study.studyboard.service.dto.response.StudyBoardDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyBoardService {

    private final StudyRepository studyRepository;
    private final StudyBoardRepository studyBoardRepository;

    public List<StudyBoardDto> findAllBoards(Long studyId) {
        List<StudyBoard> studyBoards = studyBoardRepository.findBoards(studyId);
        return studyBoards.stream()
                .map(studyBoard -> StudyBoardDto.create(studyBoard))
                .collect(Collectors.toList());
    }

    @Transactional
    public StudyBoardDto createBoard(Long studyId, StudyBoardCreateDto requestDto) {
        Study study = studyRepository.findStudyById(studyId);
        StudyBoard studyBoard = studyBoardRepository.save(requestDto.toEntity(study));
        return StudyBoardDto.create(studyBoard);
    }

    @Transactional
    public StudyBoardDto updateBoard(Long studyBoardId, StudyBoardUpdateDto requestDto) {
        StudyBoard studyBoard = findStudyBoardById(studyBoardId);
        studyBoard.update(requestDto.getTitle());
        return StudyBoardDto.create(studyBoard);
    }

    @Transactional
    public void deleteBoard(Long studyBoardId) {
        StudyBoard studyBoard = findStudyBoardById(studyBoardId);
        studyBoard.deleteInStudy();
        studyBoardRepository.delete(studyBoard);
    }

    private StudyBoard findStudyBoardById(Long studyBoardId) {
        return studyBoardRepository.findById(studyBoardId).orElseThrow(StudyBoardNotFoundException::new);
    }
}
