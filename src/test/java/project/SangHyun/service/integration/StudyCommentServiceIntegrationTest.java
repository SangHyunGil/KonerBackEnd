package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.factory.studycomment.StudyCommentFactory;
import project.SangHyun.study.studycomment.service.dto.request.StudyCommentCreateDto;
import project.SangHyun.study.studycomment.service.dto.response.StudyCommentDto;

import java.util.List;

public class StudyCommentServiceIntegrationTest extends ServiceIntegrationTest{

    @Test
    @DisplayName("새로운 댓글을 추가한다.")
    public void addComment() throws Exception {
        //given
        StudyCommentCreateDto requestDto = StudyCommentFactory.makeCreateDto(studyMember, null);

        //when
        StudyCommentDto ActualResult = studyCommentService.createComment(announceArticle.getId(), requestDto);

        //then
        Assertions.assertEquals("테스트 댓글입니다.", ActualResult.getContent());
    }

    @Test
    @DisplayName("댓글을 전체 조회한다.")
    public void findAll() throws Exception {
        //given

        //when
        List<StudyCommentDto> comments = studyCommentService.findComments(announceArticle.getId());


        //then
        Assertions.assertEquals(2, studyCommentRepository.findAll().size());
    }


    @Test
    @DisplayName("댓글에 답글을 추가한다.")
    public void addReplyComment() throws Exception {
        //given
        StudyCommentCreateDto requestDto = StudyCommentFactory.makeCreateDto(studyMember, parentComment);

        //when
        StudyCommentDto ActualResult = studyCommentService.createComment(announceArticle.getId(), requestDto);
        persistContextClear();

        //then
        Assertions.assertEquals("테스트 댓글입니다.", ActualResult.getContent());
        Assertions.assertEquals(2, studyCommentRepository.findAllByMemberId(studyMember.getId()).get(0).getChildren().size());
    }

    @Test
    @DisplayName("게시글이 삭제되면 해당 댓글들 모두 삭제된다.")
    public void onDeleteTest() throws Exception {
        //given

        //when
        studyArticleService.deleteArticle(announceArticle.getId());
        persistContextClear();

        //then
        Assertions.assertEquals(0, studyCommentRepository.findAll().size());
    }

    @Test
    @DisplayName("댓글을 삭제한다.(부모가 삭제처리되지 않았다면 자신만 삭제처리된다.)")
    public void deleteComment() throws Exception {
        //given

        //when
        studyCommentService.deleteComment(childComment.getId());

        //then
        Assertions.assertEquals(1, studyCommentRepository.findAll().size());
    }

    @Test
    @DisplayName("댓글을 삭제한다.(부모가 삭제처리되어있다면 부모와 자신 모두 삭제처리된다.)")
    public void deleteComment2() throws Exception {
        //given
        persistContextClear();
        parentComment.delete();

        //when
        studyCommentService.deleteComment(childComment.getId());


        //then
        Assertions.assertEquals(0, studyCommentRepository.findAll().size());
    }

    private void persistContextClear() {
        em.flush();
        em.clear();
    }
}
