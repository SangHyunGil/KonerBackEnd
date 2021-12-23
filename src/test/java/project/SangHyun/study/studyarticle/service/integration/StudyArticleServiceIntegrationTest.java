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
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.advice.exception.NotBelongStudyMemberException;
import project.SangHyun.advice.exception.StudyArticleNotFoundException;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.study.repository.StudyRepository;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.service.impl.StudyArticleServiceImpl;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyArticleServiceIntegrationTest {

    @Autowired
    StudyArticleServiceImpl studyArticleService;
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
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");

        //when
        StudyArticleCreateResponseDto ActualResult = studyArticleService.createArticle(study.getId(), studyBoard.getId(), requestDto);

        //then
        StudyArticleFindResponseDto ExpectResult = studyArticleService.findAllArticles(study.getId(), studyBoard.getId()).stream()
                .filter(studyArticle -> studyArticle.getTitle().equals("테스트 글"))
                .findFirst()
                .orElseThrow(StudyArticleNotFoundException::new);

        Assertions.assertEquals(ExpectResult.getArticleId(), ActualResult.getArticleId());;
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 모든 게시글을 로드한다.")
    public void loadArticles() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);

        //when
        List<StudyArticleFindResponseDto> ActualResult = studyArticleService.findAllArticles(study.getId(), studyBoard.getId());

        //then
        Assertions.assertEquals(3, ActualResult.size());;
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 수정한다.")
    public void updateArticle() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto createDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");
        StudyArticleUpdateRequestDto updateDto = new StudyArticleUpdateRequestDto("테스트 글 수정", "테스트 내용 수정");

        //when
        StudyArticleCreateResponseDto article = studyArticleService.createArticle(study.getId(), studyBoard.getId(), createDto);
        StudyArticleUpdateResponseDto ActualResult = studyArticleService.updateArticle(study.getId(), article.getArticleId(), updateDto);

        //then
        StudyArticleFindResponseDto ExpectResult = studyArticleService.findAllArticles(study.getId(), studyBoard.getId()).stream()
                .filter(studyArticle -> studyArticle.getTitle().equals("테스트 글 수정"))
                .findFirst()
                .orElseThrow(StudyArticleNotFoundException::new);
        Assertions.assertEquals(ExpectResult.getArticleId(), ActualResult.getArticleId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(ExpectResult.getContent(), ActualResult.getContent());
        Assertions.assertEquals(ExpectResult.getBoardId(), ActualResult.getBoardId());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 삭제한다.")
    public void deleteArticle() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");

        //when
        List<StudyArticle> allArticles = studyArticleRepository.findAllArticles(studyBoard.getId());
        Long id = allArticles.get(0).getId();
        studyArticleService.deleteArticle(study.getId(), id);
        List<StudyArticleFindResponseDto> allArticles1 = studyArticleService.findAllArticles(study.getId(), studyBoard.getId());

        //then
        Assertions.assertEquals(1, allArticles1.size());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 보면 조회수가 증가한다.")
    public void updateViews() throws Exception {
        //given
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticle studyArticle = studyArticleRepository.findAllArticles(studyBoard.getId()).get(0);

        //when
        StudyArticleFindResponseDto prevResult = studyArticleService.findArticle(study.getId(), studyArticle.getId());
        Assertions.assertEquals(1, prevResult.getViews());
        StudyArticleFindResponseDto ActualResult = studyArticleService.findArticle(study.getId(), studyArticle.getId());

        //then
        Assertions.assertEquals(2,ActualResult.getViews());
    }
}