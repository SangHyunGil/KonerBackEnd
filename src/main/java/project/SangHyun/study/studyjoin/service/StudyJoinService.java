package project.SangHyun.study.studyjoin.service;

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
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;
import project.SangHyun.study.studyjoin.service.dto.request.StudyJoinCreateDto;
import project.SangHyun.study.studyjoin.service.dto.response.FindJoinedStudyDto;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyJoinService {

    private final ApplicationEventPublisher eventPublisher;
    private final StudyJoinRepository studyJoinRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public void applyJoin(Long studyId, Long memberId, StudyJoinCreateDto requestDto) {
        validateJoinCondition(studyId, memberId);
        studyJoinRepository.save(requestDto.toEntity(studyId, memberId));
        notifyJoinInfoToAllAdminsAndCreator(studyId);
    }

    @Transactional
    public void acceptJoin(Long studyId, Long memberId) {
        validateJoinCondition(studyId, memberId);
        StudyJoin studyJoin = findMemberJoinInfoAboutSpecificStudy(studyId, memberId);
        studyJoin.acceptMember();
        notifyJoinInfo(studyJoin, NotificationType.ACCEPT);
    }

    @Transactional
    public void rejectJoin(Long studyId, Long memberId) {
        validateJoinCondition(studyId, memberId);
        StudyJoin studyJoin = findMemberJoinInfoAboutSpecificStudy(studyId, memberId);
        studyJoinRepository.delete(studyJoin);
        notifyJoinInfo(studyJoin, NotificationType.REJECT);
    }

    public List<FindJoinedStudyDto> findJoinedStudies(Long memberId) {
        List<Study> studies = studyJoinRepository.findStudiesByMemberId(memberId);
        return studies.stream()
                .map(FindJoinedStudyDto::create)
                .collect(Collectors.toList());
    }

    public List<StudyMembersDto> findStudyMembers(Long studyId) {
        List<StudyMembersInfoDto> studyMembers = studyJoinRepository.findStudyMembers(studyId);
        return studyMembers.stream()
                .map(StudyMembersDto::create)
                .collect(Collectors.toList());
    }

    private void validateJoinCondition(Long studyId, Long memberId) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        if (studyJoinRepository.isStudyMember(studyId, memberId))
            throw new AlreadyJoinStudyMember();
        if (study.getHeadCount() <= studyJoinRepository.findStudyJoinCount(studyId))
            throw new ExceedMaximumStudyMember();
    }

    private StudyJoin findMemberJoinInfoAboutSpecificStudy(Long studyId, Long memberId) {
        return studyJoinRepository.findApplyStudy(studyId, memberId)
                .orElseThrow(StudyJoinNotFoundException::new);
    }

    private void notifyJoinInfoToAllAdminsAndCreator(Long studyId) {
        List<StudyJoin> studyJoins = studyJoinRepository.findAdminAndCreator(studyId);
        studyJoins.forEach(
                join -> notifyJoinInfo(join, NotificationType.APPLY)
        );
    }

    private void notifyJoinInfo(StudyJoin studyJoin, NotificationType accept) {
        studyJoin.publishEvent(eventPublisher, accept);
    }
}
