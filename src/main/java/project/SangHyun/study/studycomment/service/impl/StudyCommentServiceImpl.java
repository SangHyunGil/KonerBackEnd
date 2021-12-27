package project.SangHyun.study.studycomment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.StudyCommentNotFoundException;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.dto.request.StudyCommentCreateRequestDto;
import project.SangHyun.study.studycomment.dto.request.StudyCommentUpdateRequestDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentCreateResponseDto;
import project.SangHyun.study.studycomment.dto.response.StudyCommentUpdateResponseDto;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studycomment.service.StudyCommentService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyCommentServiceImpl implements StudyCommentService {
    private final StudyCommentRepository studyCommentRepository;

    @Override
    public StudyCommentCreateResponseDto createComment(Long studyArticleId, StudyCommentCreateRequestDto requestDto) {
        StudyComment studyComment = studyCommentRepository.save(requestDto.toEntity(studyArticleId));
        return StudyCommentCreateResponseDto.create(studyComment);
    }

    @Override
    public StudyCommentUpdateResponseDto updateComment(Long studyCommentId, StudyCommentUpdateRequestDto requestDto) {
        StudyComment comment = studyCommentRepository.findById(studyCommentId).orElseThrow(StudyCommentNotFoundException::new);
        comment.update(requestDto.getContent());
        return StudyCommentUpdateResponseDto.create(comment);
    }
}
