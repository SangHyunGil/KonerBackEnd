package project.SangHyun.study.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.common.advice.exception.StudyNotFoundException;
import project.SangHyun.common.dto.SliceResponseDto;
import project.SangHyun.common.helper.AwsS3BucketHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.study.service.dto.request.StudyCreateDto;
import project.SangHyun.study.study.service.dto.response.StudyDto;
import project.SangHyun.study.study.service.dto.request.StudyUpdateDto;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {

    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final AwsS3BucketHelper awsS3BucketHelper;

    @Transactional
    public StudyDto createStudy(StudyCreateDto requestDto) throws IOException {
        Member member = findMemberById(requestDto);
        Study study = studyRepository.save(requestDto.toEntity(member, awsS3BucketHelper.store(requestDto.getProfileImg())));
        return StudyDto.create(study);
    }

    public SliceResponseDto findAllStudiesByDepartment(Long lastStudyId, StudyCategory category, Integer size) {
        Slice<Study> study = studyRepository.findAllOrderByStudyIdDesc(lastStudyId, category, Pageable.ofSize(size));
        return SliceResponseDto.create(study, StudyDto::create);
    }

    public StudyDto findStudy(Long studyId) {
        Study study = studyRepository.findStudyById(studyId);
        return StudyDto.create(study);
    }

    @Transactional
    public StudyDto updateStudy(Long studyId, StudyUpdateDto requestDto) throws IOException {
        Study study = studyRepository.findStudyById(studyId);
        Study updatedStudy = study.update(requestDto.toEntity(), awsS3BucketHelper.store(requestDto.getProfileImg()));
        return StudyDto.create(updatedStudy);
    }

    @Transactional
    public void deleteStudy(Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        studyRepository.delete(study);
    }

    private Member findMemberById(StudyCreateDto requestDto) {
        return memberRepository.findById(requestDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
    }
}
