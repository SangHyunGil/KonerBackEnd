package project.SangHyun.study.studyarticle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.common.advice.exception.StudyArticleNotFoundException;
import project.SangHyun.common.advice.exception.StudyBoardNotFoundException;
import project.SangHyun.dto.response.PageResponseDto;
import project.SangHyun.helper.AwsS3BucketHelper;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleCreateDto;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleImageUploadDto;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleUpdateDto;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleDto;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleImageDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.repository.StudyBoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyArticleService {

    private final AwsS3BucketHelper awsS3BucketHelper;
    private final MemberRepository memberRepository;
    private final StudyBoardRepository studyBoardRepository;
    private final StudyArticleRepository studyArticleRepository;

    @Transactional
    public StudyArticleDto createArticle(Long boardId, StudyArticleCreateDto requestDto) {
        Member member = findMemberById(requestDto.getMemberId());
        StudyBoard studyBoard = findStudyBoardById(boardId);
        StudyArticle studyArticle = studyArticleRepository.save(requestDto.toEntity(member, studyBoard));
        return StudyArticleDto.create(studyArticle);
    }

    public List<StudyArticleImageDto> uploadImages(StudyArticleImageUploadDto requestDto) {
        return requestDto.getMultipartFiles().stream()
                .map(awsS3BucketHelper::store)
                .map(StudyArticleImageDto::new)
                .collect(Collectors.toList());
    }

    public PageResponseDto findAllArticles(Long boardId, Integer page, Integer size) {
        Page<StudyArticle> studyArticle = studyArticleRepository.findAllOrderByStudyArticleIdDesc(boardId, PageRequest.of(page, size));
        return PageResponseDto.create(studyArticle, StudyArticleDto::create);
    }

    public StudyArticleDto findArticle(Long articleId) {
        StudyArticle studyArticle = findStudyArticleById(articleId);
        studyArticle.increaseView();
        return StudyArticleDto.create(studyArticle);
    }

    @Transactional
    public StudyArticleDto updateArticle(Long articleId, StudyArticleUpdateDto requestDto) {
        StudyArticle studyArticle = findStudyArticleById(articleId);
        studyArticle.updateArticleInfo(requestDto.toEntity());
        return StudyArticleDto.create(studyArticle);
    }

    @Transactional
    public void deleteArticle(Long articleId) {
        StudyArticle studyArticle = findStudyArticleById(articleId);
        studyArticleRepository.delete(studyArticle);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private StudyBoard findStudyBoardById(Long boardId) {
        return studyBoardRepository.findById(boardId).orElseThrow(StudyBoardNotFoundException::new);
    }

    private StudyArticle findStudyArticleById(Long articleId) {
        return studyArticleRepository.findById(articleId).orElseThrow(StudyArticleNotFoundException::new);
    }
}
