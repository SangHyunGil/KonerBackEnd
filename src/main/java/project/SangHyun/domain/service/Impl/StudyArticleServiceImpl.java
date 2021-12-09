package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.domain.entity.StudyArticle;
import project.SangHyun.domain.repository.StudyArticleRepository;
import project.SangHyun.domain.response.StudyArticleCreateResponseDto;
import project.SangHyun.domain.service.StudyArticleService;
import project.SangHyun.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.StudyArticleFindResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyArticleServiceImpl implements StudyArticleService {

    private final StudyArticleRepository studyArticleRepository;

    @Override
    @Transactional
    public StudyArticleCreateResponseDto createArticle(Long boardId, StudyArticleCreateRequestDto requestDto) {
        StudyArticle studyBoard = studyArticleRepository.save(requestDto.toEntity(boardId));
        return StudyArticleCreateResponseDto.createDto(studyBoard);
    }

    @Override
    public List<StudyArticleFindResponseDto> findAllArticles(Long boardId) {
        List<StudyArticle> studyBoards = studyArticleRepository.findAllArticles(boardId);
        return studyBoards.stream()
                .map(studyBoard -> StudyArticleFindResponseDto.createDto(studyBoard))
                .collect(Collectors.toList());
    }
}
