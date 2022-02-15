package project.SangHyun.study.videoroom.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidPinException;

class PinTest {

    @Test
    @DisplayName("화상회의 방 비밀번호가 30자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new Pin("hi".repeat(2)));
    }

    @Test
    @DisplayName("화상회의 방 비밀번호가 30자 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidPinException.class, () -> new Pin("h".repeat(31)));
    }

}