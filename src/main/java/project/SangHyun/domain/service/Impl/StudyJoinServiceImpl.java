package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.AlreadyJoinStudyMember;
import project.SangHyun.advice.exception.ExceedMaximumStudyMember;
import project.SangHyun.advice.exception.StudyNotFountException;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.StudyJoinService;
import project.SangHyun.dto.request.StudyJoinRequestDto;
import project.SangHyun.dto.response.StudyJoinResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyJoinServiceImpl implements StudyJoinService {

    private final StudyJoinRepository studyJoinRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public StudyJoinResponseDto join(StudyJoinRequestDto requestDto) {
        Study study = studyRepository.findById(requestDto.getStudyId()).orElseThrow(StudyNotFountException::new);
        Long joinCount = studyJoinRepository.findStudyJoinCount(requestDto.getStudyId());

        if (studyJoinRepository.exist(requestDto.getStudyId(), requestDto.getMemberId()))
            throw new AlreadyJoinStudyMember();

        if (study.getHeadCount() < joinCount)
            throw new ExceedMaximumStudyMember();

        StudyJoin studyJoin = studyJoinRepository.save(requestDto.toEntity());
        return StudyJoinResponseDto.createDto(studyJoin);
    }
}
