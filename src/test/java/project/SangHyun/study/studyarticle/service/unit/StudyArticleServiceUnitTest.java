package project.SangHyun.study.studyarticle.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import project.SangHyun.common.dto.PageResponseDto;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.tools.StudyFactory;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyarticle.service.StudyArticleService;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleCreateDto;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleDto;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleUpdateDto;
import project.SangHyun.study.studyarticle.tools.StudyArticleFactory;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyboard.repository.StudyBoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class StudyArticleServiceUnitTest {
    Member member;
    Study study;
    StudyBoard studyBoard;
    StudyArticle studyArticle;
    List<StudyArticle> studyArticles;

    @InjectMocks
    StudyArticleService studyArticleService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    StudyBoardRepository studyBoardRepository;
    @Mock
    StudyArticleRepository studyArticleRepository;

    @BeforeEach
    public void init() {
        member = StudyFactory.makeTestAuthMember();
        study = StudyArticleFactory.makeTestStudy(member, new ArrayList<>(), new ArrayList<>());
        studyBoard = StudyArticleFactory.makeTestStudyBoard(study);
        study.addBoard(studyBoard);
        studyArticle = StudyArticleFactory.makeTestStudyArticle(member, studyBoard);
        studyArticles = List.of(this.studyArticle);
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 생성한다.")
    public void createArticle() throws Exception {
        //given
        StudyArticleCreateDto requestDto = StudyArticleFactory.makeCreateDto(member);
        StudyArticle createdArticle = requestDto.toEntity(member, studyBoard);
        StudyArticleDto ExpectResult = StudyArticleFactory.makeDto(createdArticle);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));
        given(studyBoardRepository.findById(any())).willReturn(Optional.ofNullable(studyBoard));
        given(studyArticleRepository.save(any())).willReturn(createdArticle);

        //when
        StudyArticleDto ActualResult = studyArticleService.createArticle(studyBoard.getId(), requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 모든 게시글을 로드한다.")
    public void loadArticles() throws Exception {
        //given
        Page<StudyArticle> studyArticlesPage = new PageImpl<>(studyArticles, PageRequest.of(0, 10), studyArticles.size());

        //mocking
        given(studyArticleRepository.findAllOrderByStudyArticleIdDesc(any(), any())).willReturn(studyArticlesPage);

        //when
        PageResponseDto ActualResult = studyArticleService.findAllArticles(studyBoard.getId(), 0, 10);

        //then
        Assertions.assertEquals(1, ActualResult.getNumberOfElements());
        Assertions.assertEquals(1, ActualResult.getTotalPages());
        Assertions.assertEquals(false, ActualResult.isHasNext());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글의 세부 사항을 로드한다.")
    public void loadArticle() throws Exception {
        //given

        //mocking
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));

        //when
        StudyArticleDto ActualResult = studyArticleService.findArticle(studyArticle.getId());

        //then
        Assertions.assertEquals("테스트 글", ActualResult.getTitle());
        Assertions.assertEquals("테스트 내용", ActualResult.getContent());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 수정한다.")
    public void updateArticle() throws Exception {
        //given
        StudyArticleUpdateDto requestDto = StudyArticleFactory.makeUpdateDto("테스트 글 수정", "테스트 내용 수정");

        //mocking
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));

        //when
        StudyArticleDto ActualResult = studyArticleService.updateArticle(studyBoard.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 글 수정", ActualResult.getTitle());
        Assertions.assertEquals("테스트 내용 수정", ActualResult.getContent());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 삭제한다.")
    public void deleteArticle() throws Exception {
        //given

        //mocking
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));
        willDoNothing().given(studyArticleRepository).delete(studyArticle);

        //when, then
        Assertions.assertDoesNotThrow(() -> studyArticleService.deleteArticle(studyArticle.getId()));
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 보면 조회수가 증가한다.")
    public void updateViews() throws Exception {
        //given

        //mocking
        given(studyArticleRepository.findById(studyArticle.getId())).willReturn(Optional.of(studyArticle));

        //when
        Assertions.assertEquals(0, studyArticle.getViews());
        StudyArticleDto ActualResult = studyArticleService.findArticle(studyArticle.getId());

        //then
        Assertions.assertEquals(1,ActualResult.getViews());
    }
}