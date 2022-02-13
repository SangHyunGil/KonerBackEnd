package project.SangHyun.study.studyjoin.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidApplyContentContent;

class ApplyContentTest {

    @Test
    @DisplayName("지원서가 1000자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new ApplyContent("h".repeat(5)));
    }

    @Test
    @DisplayName("지원서가 1001자 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidApplyContentContent.class, () -> new ApplyContent("h".repeat(1001)));
    }

    @Test
    @DisplayName("지원서가 없을 경우 디폴트 문자열을 내준다.")
    public void test3() throws Exception {
        //given

        // when
        ApplyContent applyContent = new ApplyContent(null);

        // then
        Assertions.assertEquals("스터디 창설자입니다. 지원서 작성 내역이 존재하지 않습니다.", applyContent.getApplyContent());
    }
}