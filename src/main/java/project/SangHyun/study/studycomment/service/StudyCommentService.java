package project.SangHyun.study.studycomment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.common.advice.exception.StudyCommentNotFoundException;
import project.SangHyun.common.advice.exception.StudyNotFoundException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studycomment.domain.StudyComment;
import project.SangHyun.study.studycomment.repository.StudyCommentRepository;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentCreateDto;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentUpdateDto;
import project.SangHyun.study.studycomment.service.dto.response.StudyCommentDto;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyCommentService {

    private final MemberRepository memberRepository;
    private final StudyArticleRepository studyArticleRepository;
    private final StudyCommentRepository studyCommentRepository;

    @Transactional
    public StudyCommentDto createComment(Long studyArticleId, StudyCommentCreateDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(MemberNotFoundException::new);
        StudyArticle studyArticle = studyArticleRepository.findById(studyArticleId).orElseThrow(StudyNotFoundException::new);
        StudyComment studyComment = studyCommentRepository.save(requestDto.toEntity(member, studyArticle));
        return StudyCommentDto.create(studyComment);
    }

    @Transactional
    public StudyCommentDto updateComment(Long studyCommentId, StudyCommentUpdateDto requestDto) {
        StudyComment comment = findStudyCommentById(studyCommentId);
        comment.update(requestDto.getContent());
        return StudyCommentDto.create(comment);
    }

    @Transactional
    public void deleteComment(Long studyCommentId) {
        StudyComment comment = findStudyCommentById(studyCommentId);
        comment.findDeletableComment().ifPresentOrElse(studyCommentRepository::delete, comment::delete);
    }

    public List<StudyCommentDto> findComments(Long studyArticleId) {
        List<StudyComment> studyComments = studyCommentRepository.findAllByStudyArticleId(studyArticleId);
        return StudyCommentDto.create(studyComments);
    }

    private StudyComment findStudyCommentById(Long studyCommentId) {
        return studyCommentRepository.findById(studyCommentId).orElseThrow(StudyCommentNotFoundException::new);
    }
}
