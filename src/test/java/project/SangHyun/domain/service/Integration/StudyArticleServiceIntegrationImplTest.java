package project.SangHyun.domain.service.Integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.advice.exception.MemberNotFoundException;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.domain.service.Impl.StudyArticleServiceImpl;
import project.SangHyun.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.StudyArticleFindResponseDto;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudyArticleServiceIntegrationImplTest {

    @Autowired
    StudyArticleServiceImpl studyArticleService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    public void 게시글_작성_및_모두찾기() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        StudyBoard studyBoard = studyRepository.findStudyByTitle("백엔드").get(0).getStudyBoards().get(0);
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");

        //when
        StudyArticleCreateResponseDto ActualResult = studyArticleService.createArticle(studyBoard.getId(), requestDto);

        //then
        StudyArticleFindResponseDto article = studyArticleService.findAllArticles(studyBoard.getId()).get(0);
        Assertions.assertEquals(article.getArticleId(), ActualResult.getArticleId());
        Assertions.assertEquals(article.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(article.getContent(), ActualResult.getContent());
        Assertions.assertEquals(article.getBoardId(), ActualResult.getBoardId());
    }

}