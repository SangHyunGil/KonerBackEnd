package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.domain.service.StudyJoinService;
import project.SangHyun.dto.request.StudyJoinRequestDto;
import project.SangHyun.dto.response.StudyJoinCountResponseDto;
import project.SangHyun.dto.response.StudyJoinResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyJoinServiceImpl implements StudyJoinService {

    private final StudyJoinRepository studyJoinRepository;

    @Transactional
    public StudyJoinResponseDto join(StudyJoinRequestDto requestDto) {
        StudyJoin studyJoin = studyJoinRepository.save(requestDto.toEntity());
        return StudyJoinResponseDto.createDto(studyJoin);
    }
}
