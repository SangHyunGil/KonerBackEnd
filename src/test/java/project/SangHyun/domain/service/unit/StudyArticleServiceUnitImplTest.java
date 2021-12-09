package project.SangHyun.domain.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyArticle;
import project.SangHyun.domain.entity.StudyBoard;
import project.SangHyun.domain.repository.StudyArticleRepository;
import project.SangHyun.domain.response.StudyArticleCreateResponseDto;
import project.SangHyun.domain.service.Impl.StudyArticleServiceImpl;
import project.SangHyun.dto.request.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.StudyArticleFindResponseDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudyArticleServiceUnitImplTest {

    @InjectMocks
    StudyArticleServiceImpl studyArticleService;
    @Mock
    StudyArticleRepository studyArticleRepository;

    @Test
    public void 게시글_작성() throws Exception {
        //given
        Long memberId = 1L;
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(memberId, "테스트 글", "테스트 내용");

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        StudyArticleCreateResponseDto ExpectResult = StudyArticleCreateResponseDto.createDto(studyArticle);

        //mocking
        given(studyArticleRepository.save(any())).willReturn(studyArticle);

        //when
        StudyArticleCreateResponseDto ActualResult = studyArticleService.createArticle(requestDto, studyBoardId);

        //then
        Assertions.assertEquals(ExpectResult.getArticleId(), ActualResult.getArticleId());
        Assertions.assertEquals(ExpectResult.getTitle(), ActualResult.getTitle());
        Assertions.assertEquals(ExpectResult.getContent(), ActualResult.getContent());
        Assertions.assertEquals(ExpectResult.getBoardId(), ActualResult.getBoardId());
        Assertions.assertEquals(ExpectResult.getMemberId(), ActualResult.getMemberId());
    }

    @Test
    public void 게시글_모두_찾기() throws Exception {
        //given
        Long categoryId = 1L;

        Long memberId = 1L;
        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);

        //mocking
        given(studyArticleRepository.findAllArticles(any())).willReturn(List.of(studyArticle));

        //when
        List<StudyArticleFindResponseDto> ActualResult = studyArticleService.findAllArticles(categoryId);

        //then
        Assertions.assertEquals(1L, ActualResult.size());
        Assertions.assertEquals(1L, ActualResult.get(0).getBoardId());
    }
}