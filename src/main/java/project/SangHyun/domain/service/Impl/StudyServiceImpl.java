package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.StudyNotFountException;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyBoardCategory;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.service.StudyService;
import project.SangHyun.dto.request.StudyCreateRequestDto;
import project.SangHyun.dto.request.StudyUpdateRequestDto;
import project.SangHyun.dto.response.StudyCreateResponseDto;
import project.SangHyun.dto.response.StudyFindResponseDto;
import project.SangHyun.dto.response.StudyUpdateResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;
    private final StudyJoinRepository studyJoinRepository;

    @Override
    @Transactional
    public StudyCreateResponseDto createStudy(StudyCreateRequestDto requestDto) {
        Study study = studyRepository.save(makeStudy(requestDto));
        return StudyCreateResponseDto.createDto(study);
    }

    private Study makeStudy(StudyCreateRequestDto requestDto) {
        Study study = requestDto.toEntity();
        study.join(new StudyJoin(new Member(requestDto.getMemberId()), study, StudyRole.ADMIN));
        study.addBoard(new StudyBoardCategory("공지사항", study));
        study.addBoard(new StudyBoardCategory("자유게시판", study));

        return study;
    }

    @Override
    public List<StudyFindResponseDto> findAllStudies() {
        List<Study> studies = studyRepository.findAll();
        return studies.stream()
                .map(study -> StudyFindResponseDto.createDto(study))
                .collect(Collectors.toList());
    }

    @Override
    public StudyFindResponseDto findStudy(Long studyId) {
        return studyRepository.findStudyById(studyId);
    }

    @Override
    @Transactional
    public StudyUpdateResponseDto updateStudyInfo(StudyUpdateRequestDto requestDto) {
        Study study = studyRepository.findById(requestDto.getStudyId()).orElseThrow(StudyNotFountException::new);
        return StudyUpdateResponseDto.createDto(study.updateStudyInfo(requestDto));
    }
}
