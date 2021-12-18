package project.SangHyun.study.studyjoin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.AlreadyJoinStudyMember;
import project.SangHyun.advice.exception.ExceedMaximumStudyMember;
import project.SangHyun.advice.exception.StudyNotFoundException;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyjoin.service.StudyJoinService;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyJoinServiceImpl implements StudyJoinService {

    private final StudyJoinRepository studyJoinRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public StudyJoinResponseDto joinStudy(StudyJoinRequestDto requestDto) {
        validateJoinCondition(requestDto);
        StudyJoin studyJoin = studyJoinRepository.save(requestDto.toEntity());
        return StudyJoinResponseDto.create(studyJoin);
    }

    private void validateJoinCondition(StudyJoinRequestDto requestDto) {
        Study study = studyRepository.findById(requestDto.getStudyId()).orElseThrow(StudyNotFoundException::new);
        if (studyJoinRepository.exist(requestDto.getStudyId(), requestDto.getMemberId()))
            throw new AlreadyJoinStudyMember();
        if (study.getHeadCount() <= studyJoinRepository.findStudyJoinCount(requestDto.getStudyId()))
            throw new ExceedMaximumStudyMember();
    }
}
