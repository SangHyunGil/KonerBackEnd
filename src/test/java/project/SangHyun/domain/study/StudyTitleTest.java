package project.SangHyun.domain.study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidStudyTitleException;
import project.SangHyun.study.study.domain.StudyTitle;

class StudyTitleTest {

    @Test
    @DisplayName("스터디 제목이 30자 이내일 경우 성공한다.")
    public void title1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new StudyTitle("hi".repeat(2)));
    }

    @Test
    @DisplayName("스터디 제목이 30자 이상일 경우 실패한다.")
    public void title2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidStudyTitleException.class, () -> new StudyTitle("h".repeat(31)));
    }

    @Test
    @DisplayName("스터디 제목이 공백일 경우 실패한다.")
    public void title3() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidStudyTitleException.class, () -> new StudyTitle(" "));
    }
}