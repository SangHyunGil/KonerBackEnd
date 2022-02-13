package project.SangHyun.study.studyarticle.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.common.advice.exception.StudyArticleNotFoundException;
import project.SangHyun.common.dto.PageResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyarticle.service.StudyArticleService;
import project.SangHyun.study.studyarticle.service.dto.StudyArticleCreateDto;
import project.SangHyun.study.studyarticle.service.dto.StudyArticleDto;
import project.SangHyun.study.studyarticle.service.dto.StudyArticleUpdateDto;
import project.SangHyun.study.studyarticle.tools.StudyArticleFactory;
import project.SangHyun.study.studyboard.domain.StudyBoard;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyArticleServiceIntegrationTest {

    @Autowired
    StudyArticleService studyArticleService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    StudyArticleRepository studyArticleRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 생성한다.")
    public void createArticle() throws Exception {
        //given
        Member member = testDB.findStudyGeneralMember();
        StudyBoard studyBoard = testDB.findAnnounceBoard();
        StudyArticleCreateDto requestDto = StudyArticleFactory.makeCreateDto(member);

        //when
        StudyArticleDto ActualResult = studyArticleService.createArticle(studyBoard.getId(), requestDto);

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
        StudyBoard studyBoard = testDB.findAnnounceBoard();

        //when
        PageResponseDto ActualResult = studyArticleService.findAllArticles(studyBoard.getId(), 0, 10);

        //then
        Assertions.assertEquals(3, ActualResult.getNumberOfElements());
        Assertions.assertEquals(1, ActualResult.getTotalPages());
        Assertions.assertEquals(false, ActualResult.isHasNext());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 수정한다.")
    public void updateArticle() throws Exception {
        //given
        StudyArticle studyArticle = testDB.findAnnounceArticle();
        StudyArticleUpdateDto updateDto = StudyArticleFactory.makeUpdateDto("테스트 글 수정", "테스트 내용 수정");

        //when
        StudyArticleDto ActualResult = studyArticleService.updateArticle(studyArticle.getId(), updateDto);

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
        StudyArticle studyArticle = testDB.findAnnounceArticle();

        //when
        studyArticleService.deleteArticle(studyArticle.getId());

        //then
        Assertions.assertEquals(2, studyRepository.findAll().size());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 보면 조회수가 증가한다.")
    public void updateViews() throws Exception {
        //given
        StudyArticle studyArticle = testDB.findAnnounceArticle();

        //when
        Assertions.assertEquals(0, studyArticle.getViews());
        StudyArticleDto ActualResult = studyArticleService.findArticle(studyArticle.getId());

        //then`
        Assertions.assertEquals(1, ActualResult.getViews());
    }
}