package project.SangHyun.member.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidPasswordException;

class PasswordTest {

    @Test
    @DisplayName("비밀번호가 있을 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new Password("h".repeat(5)));
    }

    @Test
    @DisplayName("비밀번호가 없을 경우 실패한다.")
    public void test4() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidPasswordException.class, () -> new Password(null));
    }
}