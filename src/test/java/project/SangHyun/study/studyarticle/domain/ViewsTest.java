package project.SangHyun.study.studyarticle.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidViewsException;
import project.SangHyun.study.study.domain.HeadCount;

class ViewsTest {

    @Test
    @DisplayName("조회수가 1 이상 99 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new Views(15L));
    }

    @Test
    @DisplayName("조회수가 0 미만일 경우 실패한다.")
    public void test3() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidViewsException.class, () -> new Views(-1L));
    }

    @Test
    @DisplayName("조회수가 없을 경우 실패한다.")
    public void test4() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidViewsException.class, () -> new Views(null));
    }
}