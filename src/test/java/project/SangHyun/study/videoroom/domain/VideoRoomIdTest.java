package project.SangHyun.study.videoroom.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidVideoRoomIdException;

class VideoRoomIdTest {
    @Test
    @DisplayName("화상회의 방 번호가 존재할 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new VideoRoomId(123123123L));
    }

    @Test
    @DisplayName("화상회의 방 번호가 존재하지 않을 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidVideoRoomIdException.class, () -> new VideoRoomId(null));
    }
}