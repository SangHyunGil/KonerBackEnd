package project.SangHyun.study.studycomment.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studyboard.domain.StudyBoard;

class StudyCommentTest {
    @Test
    @DisplayName("대댓글을 추가한다.")
    public void addChild() throws Exception {
        //given
        Member memberA = new Member(1L);
        Member memberB = new Member(2L);
        StudyBoard studyBoard = new StudyBoard(1L);
        StudyArticle studyArticle = new StudyArticle("테스트 글", "테스트 글입니다.", 0L, memberA, studyBoard);
        StudyComment studyCommentA = new StudyComment(memberA, studyArticle, null, "테스트 댓글입니다.");
        StudyComment studyCommentB = new StudyComment(memberB, studyArticle, null, "테스트 대댓글입니다.");

        //when
        studyCommentA.addChild(studyCommentB);

        //then
        Assertions.assertEquals(1, studyCommentA.getChildren().size());
        Assertions.assertEquals(studyCommentA, studyCommentB.getParent());
    }
}