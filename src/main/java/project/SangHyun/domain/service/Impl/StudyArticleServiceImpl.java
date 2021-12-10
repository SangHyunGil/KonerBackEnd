package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.advice.exception.NotBelongStudyMemberException;
import project.SangHyun.advice.exception.StudyArticleNotFoundException;
import project.SangHyun.domain.entity.StudyArticle;
import project.SangHyun.domain.repository.StudyArticleRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.dto.request.study.StudyArticleUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleCreateResponseDto;
import project.SangHyun.domain.service.StudyArticleService;
import project.SangHyun.dto.request.study.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyArticleFindResponseDto;
import project.SangHyun.dto.response.study.StudyArticleUpdateResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyArticleServiceImpl implements StudyArticleService {

    private final StudyArticleRepository studyArticleRepository;
    private final StudyJoinRepository studyJoinRepository;

    @Override
    @Transactional
    public StudyArticleCreateResponseDto createArticle(Long memberId, Long studyId, Long boardId, StudyArticleCreateRequestDto requestDto) {
        validateJoinedMember(memberId, studyId);
        StudyArticle studyBoard = studyArticleRepository.save(requestDto.toEntity(boardId));
        return StudyArticleCreateResponseDto.createDto(studyBoard);
    }

    @Override
    public List<StudyArticleFindResponseDto> findAllArticles(Long memberId, Long studyId, Long boardId) {
        validateJoinedMember(memberId, studyId);
        List<StudyArticle> studyBoards = studyArticleRepository.findAllArticles(boardId);
        return studyBoards.stream()
                .map(studyBoard -> StudyArticleFindResponseDto.createDto(studyBoard))
                .collect(Collectors.toList());
    }

    @Override
    public StudyArticleFindResponseDto findArticle(Long memberId, Long studyId, Long articleId) {
        validateJoinedMember(memberId, studyId);
        StudyArticle studyArticle = studyArticleRepository.findById(articleId).orElseThrow(StudyArticleNotFoundException::new);
        return StudyArticleFindResponseDto.createDto(studyArticle);
    }

    @Override
    public StudyArticleUpdateResponseDto updateArticle(Long memberId, Long studyId, Long articleId, StudyArticleUpdateRequestDto requestDto) {
        validateJoinedMember(memberId, studyId);
        StudyArticle studyArticle = studyArticleRepository.findById(articleId).orElseThrow(StudyArticleNotFoundException::new);
        studyArticle.updateArticleInfo(requestDto);
        return StudyArticleUpdateResponseDto.createDto(studyArticle);
    }

    @Override
    public StudyArticleDeleteResponseDto deleteArticle(Long memberId, Long studyId, Long articleId) {
        validateJoinedMember(memberId, studyId);
        StudyArticle studyArticle = studyArticleRepository.findById(articleId).orElseThrow(StudyArticleNotFoundException::new);
        studyArticleRepository.delete(studyArticle);
        return StudyArticleDeleteResponseDto.createDto(studyArticle);
    }

    private void validateJoinedMember(Long memberId, Long studyId) {
        if (!studyJoinRepository.exist(studyId, memberId))
            throw new NotBelongStudyMemberException();
    }
}
