package project.SangHyun.study.studyarticle.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.StudyArticleNotFoundException;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyarticle.service.StudyArticleService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyArticleServiceImpl implements StudyArticleService {

    private final StudyArticleRepository studyArticleRepository;

    @Override
    @Transactional
    public StudyArticleCreateResponseDto createArticle(Long boardId, StudyArticleCreateRequestDto requestDto) {
        StudyArticle studyBoard = studyArticleRepository.save(requestDto.toEntity(boardId));
        return StudyArticleCreateResponseDto.create(studyBoard);
    }

    @Override
    public List<StudyArticleFindResponseDto> findAllArticles(Long boardId) {
        List<StudyArticle> studyBoards = studyArticleRepository.findAllArticles(boardId);
        return studyBoards.stream()
                .map(studyBoard -> StudyArticleFindResponseDto.create(studyBoard))
                .collect(Collectors.toList());
    }

    @Override
    public StudyArticleFindResponseDto findArticle(Long articleId) {
        StudyArticle studyArticle = studyArticleRepository.findById(articleId).orElseThrow(StudyArticleNotFoundException::new);
        studyArticle.updateViews();
        return StudyArticleFindResponseDto.create(studyArticle);
    }

    @Override
    @Transactional
    public StudyArticleUpdateResponseDto updateArticle(Long articleId, StudyArticleUpdateRequestDto requestDto) {
        StudyArticle studyArticle = studyArticleRepository.findById(articleId).orElseThrow(StudyArticleNotFoundException::new);
        studyArticle.updateArticleInfo(requestDto);
        return StudyArticleUpdateResponseDto.create(studyArticle);
    }

    @Override
    @Transactional
    public StudyArticleDeleteResponseDto deleteArticle(Long articleId) {
        StudyArticle studyArticle = studyArticleRepository.findById(articleId).orElseThrow(StudyArticleNotFoundException::new);
        studyArticleRepository.delete(studyArticle);
        return StudyArticleDeleteResponseDto.create(studyArticle);
    }
}
