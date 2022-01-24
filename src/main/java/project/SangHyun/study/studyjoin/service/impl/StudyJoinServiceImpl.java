package project.SangHyun.study.studyjoin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.AlreadyJoinStudyMember;
import project.SangHyun.common.advice.exception.ExceedMaximumStudyMember;
import project.SangHyun.common.advice.exception.StudyJoinNotFoundException;
import project.SangHyun.common.advice.exception.StudyNotFoundException;
import project.SangHyun.notification.domain.NotificationType;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.dto.request.StudyJoinRequestDto;
import project.SangHyun.study.studyjoin.dto.response.FindJoinedStudyResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyFindMembersResponseDto;
import project.SangHyun.study.studyjoin.dto.response.StudyJoinResponseDto;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.StudyJoinService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyJoinServiceImpl implements StudyJoinService {
    private final ApplicationEventPublisher eventPublisher;
    private final StudyJoinRepository studyJoinRepository;
    private final StudyRepository studyRepository;

    @Override
    @Transactional
    public StudyJoinResponseDto applyJoin(Long studyId, Long memberId, StudyJoinRequestDto requestDto) {
        validateJoinCondition(studyId, memberId);
        StudyJoin studyJoin = studyJoinRepository.save(requestDto.toEntity(studyId, memberId));
        List<StudyJoin> studyJoins = studyJoinRepository.findAdminAndCreator(studyId);
        studyJoins.forEach(
                join -> join.publishEvent(eventPublisher, NotificationType.APPLY)
        );
        return StudyJoinResponseDto.create(studyJoin);
    }

    private void validateJoinCondition(Long studyId, Long memberId) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        if (studyJoinRepository.exist(studyId, memberId))
            throw new AlreadyJoinStudyMember();
        if (study.getHeadCount() <= studyJoinRepository.findStudyJoinCount(studyId))
            throw new ExceedMaximumStudyMember();
    }

    @Override
    @Transactional
    public StudyJoinResponseDto acceptJoin(Long studyId, Long memberId) {
        validateJoinCondition(studyId, memberId);
        StudyJoin studyJoin = studyJoinRepository.findApplyStudy(studyId, memberId)
                .orElseThrow(StudyJoinNotFoundException::new);
        studyJoin.acceptMember();
        studyJoin.publishEvent(eventPublisher, NotificationType.ACCEPT);
        return StudyJoinResponseDto.create(studyJoin);
    }

    @Override
    @Transactional
    public StudyJoinResponseDto rejectJoin(Long studyId, Long memberId) {
        validateJoinCondition(studyId, memberId);
        StudyJoin studyJoin = studyJoinRepository.findApplyStudy(studyId, memberId)
                .orElseThrow(StudyJoinNotFoundException::new);
        studyJoinRepository.delete(studyJoin);
        studyJoin.publishEvent(eventPublisher, NotificationType.REJECT);
        return StudyJoinResponseDto.create(studyJoin);
    }

    @Override
    public List<FindJoinedStudyResponseDto> findJoinedStudies(Long memberId) {
        List<Study> studies = studyJoinRepository.findStudiesByMemberId(memberId);
        return studies.stream()
                .map(study -> FindJoinedStudyResponseDto.create(study))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudyFindMembersResponseDto> findStudyMembers(Long studyId) {
        List<StudyMembersInfoDto> studyMembers = studyJoinRepository.findStudyMembers(studyId);
        return studyMembers.stream()
                .map(studyMember -> StudyFindMembersResponseDto.create(studyMember))
                .collect(Collectors.toList());
    }


}
