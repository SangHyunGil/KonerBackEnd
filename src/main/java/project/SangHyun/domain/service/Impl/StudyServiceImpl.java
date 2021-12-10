package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.NotBelongStudyMemberException;
import project.SangHyun.advice.exception.StudyHasNoProperRoleException;
import project.SangHyun.advice.exception.StudyNotFoundException;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.StudyRole;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.domain.service.StudyService;
import project.SangHyun.dto.request.study.StudyCreateRequestDto;
import project.SangHyun.dto.request.study.StudyUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyCreateResponseDto;
import project.SangHyun.dto.response.study.StudyDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyFindResponseDto;
import project.SangHyun.dto.response.study.StudyUpdateResponseDto;

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
        study.join(new StudyJoin(new Member(requestDto.getMemberId()), study, StudyRole.CREATOR));
        study.addBoard(new StudyBoard("공지사항", study));
        study.addBoard(new StudyBoard("자유게시판", study));

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
        Study study = studyRepository.findStudyById(studyId);
        return StudyFindResponseDto.createDto(study);
    }

    @Override
    @Transactional
    public StudyUpdateResponseDto updateStudyInfo(Long memberId, Long studyId, StudyUpdateRequestDto requestDto) {
        validateAuthority(memberId, studyId);
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        return StudyUpdateResponseDto.createDto(study.updateStudyInfo(requestDto));
    }

    @Override
    @Transactional
    public StudyDeleteResponseDto deleteStudy(Long memberId, Long studyId) {
        validateAuthority(memberId, studyId);
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        studyRepository.delete(study);
        return StudyDeleteResponseDto.createDto(study);
    }

    private void validateAuthority(Long memberId, Long studyId) {
        StudyJoin studyJoin = studyJoinRepository.findByMemberIdAndStudyId(memberId, studyId).orElseThrow(NotBelongStudyMemberException::new);
        if (!studyJoin.getStudyRole().equals(StudyRole.CREATOR))
            throw new StudyHasNoProperRoleException();
    }
}
