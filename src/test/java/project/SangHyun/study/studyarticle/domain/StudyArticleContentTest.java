package project.SangHyun.study.studyarticle.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidStudyArticleContentException;

class StudyArticleContentTest {

    @Test
    @DisplayName("스터디 게시글 내용이 1000자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new StudyArticleContent("h".repeat(5)));
    }

    @Test
    @DisplayName("스터디 게시글 내용이 1001자 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidStudyArticleContentException.class, () -> new StudyArticleContent("h".repeat(1001)));
    }

    @Test
    @DisplayName("스터디 게시글 내용이 없을 경우 실패한다.")
    public void test3() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidStudyArticleContentException.class, () -> new StudyArticleContent(null));
    }
}