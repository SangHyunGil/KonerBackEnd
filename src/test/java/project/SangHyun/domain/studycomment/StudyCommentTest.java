package project.SangHyun.domain.studycomment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studycomment.domain.StudyComment;

class StudyCommentTest {
    @Test
    @DisplayName("대댓글을 추가한다.")
    public void addChild() throws Exception {
        //given
        Member memberA = new Member(1L);
        Member memberB = new Member(2L);
        StudyBoard studyBoard = new StudyBoard(1L);
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, memberA, studyBoard);
        StudyComment studyCommentA = new StudyComment(memberA, studyArticle, null, "테스트 댓글입니다.", false);
        StudyComment studyCommentB = new StudyComment(memberB, studyArticle, null, "테스트 대댓글입니다.", false);

        //when
        studyCommentA.addChild(studyCommentB);

        //then
        Assertions.assertEquals(1, studyCommentA.getChildren().size());
        Assertions.assertEquals(studyCommentA, studyCommentB.getParent());
    }

    @Test
    @DisplayName("댓글을 업데이트한다.")
    public void updateComment() throws Exception {
        //given
        Member member = new Member(1L);
        StudyBoard studyBoard = new StudyBoard(1L);
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, member, studyBoard);
        StudyComment studyComment = new StudyComment(member, studyArticle, null, "테스트 댓글입니다.", false);

        //when
        studyComment.update("테스트 댓글 수정입니다.");

        //then
        Assertions.assertEquals("테스트 댓글 수정입니다.", studyComment.getContent());
    }

    @Test
    @DisplayName("댓글을 삭제 처리한다.")
    public void deleteComment() throws Exception {
        //given
        Member member = new Member(1L);
        StudyBoard studyBoard = new StudyBoard(1L);
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, member, studyBoard);
        StudyComment studyComment = new StudyComment(member, studyArticle, null, "테스트 댓글입니다.", false);

        //when
        studyComment.delete();

        //then
        Assertions.assertEquals(true, studyComment.getDeleted());
    }

    @Test
    @DisplayName("삭제 루트 부모를 찾는다. (자신이 삭제 루트 부모이다.)")
    public void deleteCommentProcess() throws Exception {
        //given
        Member memberA = new Member(1L);
        Member memberB = new Member(2L);
        StudyBoard studyBoard = new StudyBoard(1L);
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, memberA, studyBoard);
        StudyComment studyCommentA = new StudyComment(memberA, studyArticle, null, "테스트 댓글입니다.", false);
        StudyComment studyCommentB = new StudyComment(memberB, studyArticle, null, "테스트 대댓글입니다.", true);
        studyCommentA.addChild(studyCommentB);

        //when
        StudyComment comment = studyCommentB.findDeletableComment().get();

        //then
        Assertions.assertEquals(studyCommentB, comment);
    }

    @Test
    @DisplayName("삭제 루트 부모를 찾는다. (부모가 삭제 루트 부모이다.)")
    public void deleteCommentProcess2() throws Exception {
        //given
        Member memberA = new Member(1L);
        Member memberB = new Member(2L);
        StudyBoard studyBoard = new StudyBoard(1L);
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, memberA, studyBoard);
        StudyComment studyCommentA = new StudyComment(memberA, studyArticle, null, "테스트 댓글입니다.", true);
        StudyComment studyCommentB = new StudyComment(memberB, studyArticle, null, "테스트 대댓글입니다.", true);
        studyCommentA.addChild(studyCommentB);

        //when
        StudyComment comment = studyCommentB.findDeletableComment().get();

        //then
        Assertions.assertEquals(studyCommentA, comment);
    }
}