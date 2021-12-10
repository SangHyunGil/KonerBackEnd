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
import project.SangHyun.advice.exception.NotBelongStudyMemberException;
import project.SangHyun.advice.exception.StudyArticleNotFoundException;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.repository.MemberRepository;
import project.SangHyun.domain.repository.StudyRepository;
import project.SangHyun.dto.request.study.StudyArticleUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleCreateResponseDto;
import project.SangHyun.domain.service.Impl.StudyArticleServiceImpl;
import project.SangHyun.dto.request.study.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyArticleFindResponseDto;
import project.SangHyun.dto.response.study.StudyArticleUpdateResponseDto;

import javax.persistence.EntityManager;

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
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");

        //when
        StudyArticleCreateResponseDto ActualResult = studyArticleService.createArticle(member.getId(), study.getId(), studyBoard.getId(), requestDto);

        //then
        StudyArticleFindResponseDto ExpectResult = studyArticleService.findAllArticles(member.getId(), study.getId(), studyBoard.getId()).stream()
                .filter(studyArticle -> studyArticle.getTitle().equals("테스트 글"))
                .findFirst()
                .orElseThrow(StudyArticleNotFoundException::new);
        Assertions.assertEquals(ExpectResult.getArticleId(), ActualResult.getArticleId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(ExpectResult.getContent(), ActualResult.getContent());
        Assertions.assertEquals(ExpectResult.getBoardId(), ActualResult.getBoardId());
    }

    @Test
    public void 게시글_작성_및_수정_권한O() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto createDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");
        StudyArticleUpdateRequestDto updateDto = new StudyArticleUpdateRequestDto("테스트 글 수정", "테스트 내용 수정");

        //when
        StudyArticleCreateResponseDto article = studyArticleService.createArticle(member.getId(), study.getId(), studyBoard.getId(), createDto);
        StudyArticleUpdateResponseDto ActualResult = studyArticleService.updateArticle(member.getId(), study.getId(), article.getArticleId(), updateDto);

        //then
        StudyArticleFindResponseDto ExpectResult = studyArticleService.findAllArticles(member.getId(), study.getId(), studyBoard.getId()).stream()
                .filter(studyArticle -> studyArticle.getTitle().equals("테스트 글 수정"))
                .findFirst()
                .orElseThrow(StudyArticleNotFoundException::new);
        Assertions.assertEquals(ExpectResult.getArticleId(), ActualResult.getArticleId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(ExpectResult.getContent(), ActualResult.getContent());
        Assertions.assertEquals(ExpectResult.getBoardId(), ActualResult.getBoardId());
    }

    @Test
    public void 게시글_작성_및_수정_권한X() throws Exception {
        //given
        Member creator = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto createDto = new StudyArticleCreateRequestDto(creator.getId(), "테스트 글", "테스트 내용");
        StudyArticleUpdateRequestDto updateDto = new StudyArticleUpdateRequestDto("테스트 글 수정", "테스트 내용 수정");

        //when
        studyArticleService.createArticle(creator.getId(), study.getId(), studyBoard.getId(), createDto);

        //then
        Assertions.assertThrows(NotBelongStudyMemberException.class, ()->studyArticleService.updateArticle(member.getId(), study.getId(), studyBoard.getId(), updateDto));
    }


    @Test
    public void 게시글_작성_및_삭제_권한O() throws Exception {
        //given
        Member member = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");

        //when
        StudyArticleCreateResponseDto createArticle = studyArticleService.createArticle(member.getId(), study.getId(), studyBoard.getId(), requestDto);
        StudyArticleDeleteResponseDto ActualResult = studyArticleService.deleteArticle(member.getId(), study.getId(), createArticle.getArticleId());

        //then
        Assertions.assertEquals("테스트 글", ActualResult.getTitle());
    }

    @Test
    public void 게시글_작성_및_삭제_권한X() throws Exception {
        //given
        Member creator = memberRepository.findByEmail("xptmxm3!").orElseThrow(MemberNotFoundException::new);
        Member member = memberRepository.findByEmail("xptmxm1!").orElseThrow(MemberNotFoundException::new);
        Study study = studyRepository.findStudyByTitle("백엔드").get(0);
        StudyBoard studyBoard = study.getStudyBoards().get(0);
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(member.getId(), "테스트 글", "테스트 내용");

        //when
        StudyArticleCreateResponseDto createArticle = studyArticleService.createArticle(creator.getId(), study.getId(), studyBoard.getId(), requestDto);

        //then
        Assertions.assertThrows(NotBelongStudyMemberException.class, ()->studyArticleService.deleteArticle(member.getId(), study.getId(), createArticle.getArticleId()));
    }
}