package project.SangHyun.domain.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidApplyContentContent;
import project.SangHyun.message.domain.MessageContent;
import project.SangHyun.study.studyjoin.domain.ApplyContent;

class MessageContentTest {

    @Test
    @DisplayName("쪽지 내용이 1000자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new MessageContent("h".repeat(5)));
    }

    @Test
    @DisplayName("쪽지 내용이 1001자 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidApplyContentContent.class, () -> new ApplyContent("h".repeat(1001)));
    }

    @Test
    @DisplayName("쪽지 내용이 없을 경우 디폴트 문자열을 내준다.")
    public void test3() throws Exception {
        //given

        // when
        ApplyContent applyContent = new ApplyContent(null);

        // then
        Assertions.assertEquals("스터디 창설자입니다. 지원서 작성 내역이 존재하지 않습니다.", applyContent.getApplyContent());
    }
}