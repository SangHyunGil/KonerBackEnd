package project.SangHyun.study.study.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.StudyNotFoundException;
import project.SangHyun.common.dto.SliceResponseDto;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.dto.request.StudyCreateRequestDto;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.dto.response.*;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.study.service.StudyService;
import project.SangHyun.common.helper.FileStoreHelper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService {
    private final StudyRepository studyRepository;
    private final FileStoreHelper fileStoreHelper;

    @Override
    @Transactional
    public StudyCreateResponseDto createStudy(StudyCreateRequestDto requestDto) throws IOException {
        Study study = studyRepository.save(requestDto.toEntity(fileStoreHelper.storeFile(requestDto.getProfileImg())));
        return StudyCreateResponseDto.create(study);
    }

    @Override
    public SliceResponseDto findAllStudiesByDepartment(Long lastStudyId, String department, Integer size) {
        Slice<Study> study = studyRepository.findAllOrderByStudyIdDesc(lastStudyId, department, Pageable.ofSize(size));
        return SliceResponseDto.create(study, StudyFindResponseDto::create);
    }

    @Override
    public StudyFindResponseDto findStudy(Long studyId) {
        Study study = studyRepository.findStudyById(studyId);
        return StudyFindResponseDto.create(study);
    }

    @Override
    @Transactional
    public StudyUpdateResponseDto updateStudy(Long studyId, StudyUpdateRequestDto requestDto) throws IOException {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        return StudyUpdateResponseDto.create(study.update(requestDto, fileStoreHelper.storeFile(requestDto.getProfileImg())));
    }

    @Override
    @Transactional
    public StudyDeleteResponseDto deleteStudy(Long studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(StudyNotFoundException::new);
        studyRepository.delete(study);
        return StudyDeleteResponseDto.create(study);
    }
}
