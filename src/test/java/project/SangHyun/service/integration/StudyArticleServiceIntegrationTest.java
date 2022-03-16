package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.StudyArticleNotFoundException;
import project.SangHyun.dto.response.PageResponseDto;
import project.SangHyun.factory.studyarticle.StudyArticleFactory;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleCreateDto;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleUpdateDto;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleDto;

class StudyArticleServiceIntegrationTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 생성한다.")
    public void createArticle() throws Exception {
        //given
        StudyArticleCreateDto requestDto = StudyArticleFactory.makeCreateDto(studyMember);

        //when
        StudyArticleDto ActualResult = studyArticleService.createArticle(announceBoard.getId(), requestDto);

        //then
        StudyArticle ExpectResult = studyArticleRepository.findAll().stream()
                .filter(studyArticle -> studyArticle.getTitle().equals(requestDto.getTitle()))
                .findFirst()
                .orElseThrow(StudyArticleNotFoundException::new);

        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());;
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 모든 게시글을 로드한다.")
    public void loadArticles() throws Exception {
        //given

        //when
        PageResponseDto ActualResult = studyArticleService.findAllArticles(announceBoard.getId(), 0, 10);

        //then
        Assertions.assertEquals(3, ActualResult.getNumberOfElements());
        Assertions.assertEquals(1, ActualResult.getTotalPages());
        Assertions.assertEquals(false, ActualResult.isHasNext());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 수정한다.")
    public void updateArticle() throws Exception {
        //given
        StudyArticleUpdateDto updateDto = StudyArticleFactory.makeUpdateDto("테스트 글 수정", "테스트 내용 수정");

        //when
        StudyArticleDto ActualResult = studyArticleService.updateArticle(announceArticle.getId(), updateDto);

        //then
        StudyArticle ExpectResult = studyArticleRepository.findArticleByTitle("테스트 글 수정").stream()
                .filter(sa -> sa.getTitle().equals("테스트 글 수정"))
                .findFirst()
                .orElseThrow(StudyArticleNotFoundException::new);
        Assertions.assertEquals(ExpectResult.getId(), ActualResult.getId());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 삭제한다.")
    public void deleteArticle() throws Exception {
        //given

        //when
        studyArticleService.deleteArticle(announceArticle.getId());

        //then
        Assertions.assertEquals(2, studyRepository.findAll().size());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 보면 조회수가 증가한다.")
    public void updateViews() throws Exception {
        //given

        //when
        Assertions.assertEquals(0, announceArticle.getViews());
        StudyArticleDto ActualResult = studyArticleService.findArticle(announceArticle.getId());

        //then`
        Assertions.assertEquals(1, ActualResult.getViews());
    }
}