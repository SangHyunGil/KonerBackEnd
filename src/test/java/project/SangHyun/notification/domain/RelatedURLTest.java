package project.SangHyun.notification.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidRelatedURLException;

class RelatedURLTest {

    @Test
    @DisplayName("관련 링크가 공백이 아닐 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new RelatedURL("koner.kr/study"));
    }

    @Test
    @DisplayName("관련 링크가 공백일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidRelatedURLException.class, () -> new RelatedURL(null));
    }
}