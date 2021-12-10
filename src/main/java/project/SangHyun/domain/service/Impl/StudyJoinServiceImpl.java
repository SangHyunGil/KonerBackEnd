package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.AlreadyJoinStudyMember;
import project.SangHyun.advice.exception.ExceedMaximumStudyMember;
import project.SangHyun.advice.exception.StudyNotFoundException;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.StudyJoinService;
import project.SangHyun.dto.request.study.StudyJoinRequestDto;
import project.SangHyun.dto.response.study.StudyJoinResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyJoinServiceImpl implements StudyJoinService {

    private final StudyJoinRepository studyJoinRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public StudyJoinResponseDto join(StudyJoinRequestDto requestDto) {
        validateJoinCondition(requestDto);
        StudyJoin studyJoin = studyJoinRepository.save(requestDto.toEntity());
        return StudyJoinResponseDto.createDto(studyJoin);
    }

    private void validateJoinCondition(StudyJoinRequestDto requestDto) {
        Study study = studyRepository.findById(requestDto.getStudyId()).orElseThrow(StudyNotFoundException::new);
        if (studyJoinRepository.exist(requestDto.getStudyId(), requestDto.getMemberId()))
            throw new AlreadyJoinStudyMember();
        if (study.getHeadCount() <= studyJoinRepository.findStudyJoinCount(requestDto.getStudyId()))
            throw new ExceedMaximumStudyMember();
    }
}
