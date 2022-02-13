package project.SangHyun.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidEmailException;

class EmailTest {

    @Test
    @DisplayName("이메일이 12자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new Email("h".repeat(5)));
    }

    @Test
    @DisplayName("이메일이 13자 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidEmailException.class, () -> new Email("h".repeat(13)));
    }

    @Test
    @DisplayName("이메일이 공백일 경우 실패한다.")
    public void test3() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidEmailException.class, () -> new Email(" "));
    }
}