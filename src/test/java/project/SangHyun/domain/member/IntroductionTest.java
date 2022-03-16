package project.SangHyun.domain.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidIntroductionException;
import project.SangHyun.member.domain.Introduction;

class IntroductionTest {

    @Test
    @DisplayName("소개글이 1000자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new Introduction("h".repeat(5)));
    }

    @Test
    @DisplayName("소개글이 1001자 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidIntroductionException.class, () -> new Introduction("h".repeat(1001)));
    }

    @Test
    @DisplayName("소개글이 없을 경우 디폴트 문자열을 내준다.")
    public void test3() throws Exception {
        //given

        // when
        Introduction introduction = new Introduction(" ");

        // then
        Assertions.assertEquals("작성된 소개글이 존재하지 않습니다.", introduction.getIntroduction());
    }
}