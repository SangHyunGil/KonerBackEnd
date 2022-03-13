package project.SangHyun.domain.studycomment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidStudyCommentContentException;
import project.SangHyun.study.studycomment.domain.StudyCommentContent;

class StudyCommentContentTest {

    @Test
    @DisplayName("스터디 댓글 내용이 100자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new StudyCommentContent("h".repeat(5)));
    }

    @Test
    @DisplayName("스터디 댓글 내용이 101자 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidStudyCommentContentException.class, () -> new StudyCommentContent("h".repeat(101)));
    }

    @Test
    @DisplayName("스터디 게시글 내용이 없을 경우 실패한다.")
    public void test3() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidStudyCommentContentException.class, () -> new StudyCommentContent(null));
    }
}