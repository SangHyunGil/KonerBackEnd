package project.SangHyun.study.study.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidHeadCountException;

class HeadCountTest {

    @Test
    @DisplayName("인원수가 1 이상 99 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new HeadCount(15L));
    }

    @Test
    @DisplayName("인원수가 99 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidHeadCountException.class, () -> new HeadCount(100L));
    }

    @Test
    @DisplayName("인원수가 1 미만일 경우 실패한다.")
    public void test3() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidHeadCountException.class, () -> new HeadCount(0L));
    }

    @Test
    @DisplayName("인원수가 없을 경우 실패한다.")
    public void test4() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidHeadCountException.class, () -> new HeadCount(null));
    }
}