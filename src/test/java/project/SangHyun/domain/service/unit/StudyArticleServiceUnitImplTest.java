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
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.repository.StudyArticleRepository;
import project.SangHyun.domain.repository.StudyJoinRepository;
import project.SangHyun.dto.request.study.StudyArticleUpdateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleCreateResponseDto;
import project.SangHyun.domain.service.Impl.StudyArticleServiceImpl;
import project.SangHyun.dto.request.study.StudyArticleCreateRequestDto;
import project.SangHyun.dto.response.study.StudyArticleDeleteResponseDto;
import project.SangHyun.dto.response.study.StudyArticleFindResponseDto;
import project.SangHyun.dto.response.study.StudyArticleUpdateResponseDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class StudyArticleServiceUnitImplTest {

    @InjectMocks
    StudyArticleServiceImpl studyArticleService;
    @Mock
    StudyArticleRepository studyArticleRepository;
    @Mock
    StudyJoinRepository studyJoinRepository;

    @Test
    public void 게시글_작성() throws Exception {
        //given
        Long memberId = 1L;
        Long studyId = 1L;
        StudyArticleCreateRequestDto requestDto = new StudyArticleCreateRequestDto(memberId, "테스트 글", "테스트 내용");

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);
        StudyArticleCreateResponseDto ExpectResult = StudyArticleCreateResponseDto.createDto(studyArticle);

        //mocking
        given(studyArticleRepository.save(any())).willReturn(studyArticle);
        given(studyJoinRepository.exist(studyId, memberId)).willReturn(true);

        //when
        StudyArticleCreateResponseDto ActualResult = studyArticleService.createArticle(memberId, studyId, studyBoardId, requestDto);

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
        Long studyId = 1L;
        Long memberId = 1L;

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);

        //mocking
        given(studyArticleRepository.findAllArticles(any())).willReturn(List.of(studyArticle));
        given(studyJoinRepository.exist(studyId, memberId)).willReturn(true);

        //when
        List<StudyArticleFindResponseDto> ActualResult = studyArticleService.findAllArticles(memberId, studyId, studyBoardId);

        //then
        Assertions.assertEquals(1L, ActualResult.size());
        Assertions.assertEquals(1L, ActualResult.get(0).getBoardId());
    }

    @Test
    public void 게시글_찾기() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);

        //mocking
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));
        given(studyJoinRepository.exist(studyId, memberId)).willReturn(true);
        //when
        StudyArticleFindResponseDto ActualResult = studyArticleService.findArticle(memberId, studyId, studyBoardId);

        //then
        Assertions.assertEquals("테스트 글", ActualResult.getTitle());
        Assertions.assertEquals("테스트 내용", ActualResult.getContent());
    }

    @Test
    public void 게시글_수정() throws Exception {
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
        given(studyJoinRepository.exist(studyId, memberId)).willReturn(true);
        //when
        StudyArticleUpdateResponseDto ActualResult = studyArticleService.updateArticle(memberId, studyId, studyBoardId, requestDto);

        //then
        Assertions.assertEquals("테스트 글 수정", ActualResult.getTitle());
        Assertions.assertEquals("테스트 내용 수정", ActualResult.getContent());
    }

    @Test
    public void 게시글_삭제() throws Exception {
        //given
        Long studyId = 1L;
        Long memberId = 1L;

        Long studyBoardId = 1L;
        Long studyArticleId = 1L;
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 내용", new Member(memberId), new StudyBoard(studyBoardId));
        ReflectionTestUtils.setField(studyArticle, "id", studyArticleId);

        //mocking
        given(studyArticleRepository.findById(any())).willReturn(Optional.ofNullable(studyArticle));
        given(studyJoinRepository.exist(studyId, memberId)).willReturn(true);
        willDoNothing().given(studyArticleRepository).delete(studyArticle);
        //when
        StudyArticleDeleteResponseDto ActualResult = studyArticleService.deleteArticle(memberId, studyId, studyBoardId);

        //then
        Assertions.assertEquals("테스트 글", ActualResult.getTitle());
    }
}