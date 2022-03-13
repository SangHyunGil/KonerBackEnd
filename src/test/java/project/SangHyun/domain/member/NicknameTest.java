package project.SangHyun.domain.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidNicknameException;
import project.SangHyun.member.domain.Nickname;

class NicknameTest {

    @Test
    @DisplayName("닉네임이 2자 이상 12자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new Nickname("h".repeat(5)));
    }

    @Test
    @DisplayName("닉네임이 2자 미만일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidNicknameException.class, () -> new Nickname("h".repeat(1)));
    }

    @Test
    @DisplayName("닉네임이 12자 이상일 경우 실패한다.")
    public void test3() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidNicknameException.class, () -> new Nickname("h".repeat(13)));
    }

    @Test
    @DisplayName("닉네임이 없을 경우 실패한다.")
    public void test4() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidNicknameException.class, () -> new Nickname(null));
    }
}