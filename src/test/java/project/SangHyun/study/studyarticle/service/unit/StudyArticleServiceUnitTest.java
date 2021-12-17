package project.SangHyun.study.studyarticle.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyarticle.repository.StudyArticleRepository;
import project.SangHyun.study.studyjoin.repository.StudyJoinRepository;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleCreateResponseDto;
import project.SangHyun.study.studyarticle.service.impl.StudyArticleServiceImpl;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleDeleteResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleFindResponseDto;
import project.SangHyun.study.studyarticle.dto.response.StudyArticleUpdateResponseDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class StudyArticleServiceUnitTest {

    @InjectMocks
    StudyArticleServiceImpl studyArticleService;
    @Mock
    StudyArticleRepository studyArticleRepository;
    @Mock
    StudyJoinRepository studyJoinRepository;

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 생성한다.")
    public void createArticle() throws Exception {
        //given
        Long memberId = 1L;
        Long studyId = 1L;
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(memberId, "테스트 글", "테스트 내용");

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        StudyArticleCreateResponseDto ExpectResult = StudyArticleCreateResponseDto.create(studyArticle);

        //mocking
        given(studyArticleRepository.save(any())).willReturn(studyArticle);

        //when
        StudyArticleCreateResponseDto ActualResult = studyArticleService.createArticle(studyId, studyBoardId, requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getArticleId(), ActualResult.getArticleId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(ExpectResult.getContent(), ActualResult.getContent());
        Assertions.assertEquals(ExpectResult.getBoardId(), ActualResult.getBoardId());
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 모든 게시글을 로드한다.")
    public void loadArticles() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);

        //mocking
        given(studyArticleRepository.findAllArticles(any())).willReturn(List.of(studyArticle));


        //when
        List<StudyArticleFindResponseDto> ActualResult = studyArticleService.findAllArticles(studyId, studyBoardId);

        //then
        Assertions.assertEquals(1L, ActualResult.size());
        Assertions.assertEquals(1L, ActualResult.get(0).getBoardId());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글의 세부 사항을 로드한다.")
    public void loadArticle() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);

        //mocking
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));

        //when
        StudyArticleFindResponseDto ActualResult = studyArticleService.findArticle(studyId, studyBoardId);

        //then
        Assertions.assertEquals("테스트 글", ActualResult.getTitle());
        Assertions.assertEquals("테스트 내용", ActualResult.getContent());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 수정한다.")
    public void updateArticle() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);

        StudyArticleUpdateRequestDto requestDto = new StudyArticleUpdateRequestDto("테스트 글 수정", "테스트 내용 수정");

        //mocking
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));

        //when
        StudyArticleUpdateResponseDto ActualResult = studyArticleService.updateArticle(studyId, studyBoardId, requestDto);

        //then
        Assertions.assertEquals("테스트 글 수정", ActualResult.getTitle());
        Assertions.assertEquals("테스트 내용 수정", ActualResult.getContent());
    }

    @Test
    @DisplayName("스터디의 한 카테고리에 해당하는 게시글을 삭제한다.")
    public void deleteArticle() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);

        //mocking
        willDoNothing().given(studyArticleRepository).delete(studyArticle);

        //when
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));
        StudyArticleDeleteResponseDto ActualResult = studyArticleService.deleteArticle(studyId, studyBoardId);

        //then
        Assertions.assertEquals("테스트 글", ActualResult.getTitle());
    }
}