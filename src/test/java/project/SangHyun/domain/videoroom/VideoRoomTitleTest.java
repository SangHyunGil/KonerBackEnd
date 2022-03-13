package project.SangHyun.domain.videoroom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.common.advice.exception.InvalidVideoRoomTitleException;
import project.SangHyun.study.videoroom.domain.VideoRoomTitle;

class VideoRoomTitleTest {

    @Test
    @DisplayName("화상회의 방 제목이 30자 이내일 경우 성공한다.")
    public void test1() throws Exception {
        //given

        //when, then
        Assertions.assertDoesNotThrow(() -> new VideoRoomTitle("hi".repeat(2)));
    }

    @Test
    @DisplayName("화상회의 방 제목이 30자 이상일 경우 실패한다.")
    public void test2() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidVideoRoomTitleException.class, () -> new VideoRoomTitle("h".repeat(31)));
    }

    @Test
    @DisplayName("화상회의 방 제목이 공백일 경우 실패한다.")
    public void test3() throws Exception {
        //given

        //when, then
        Assertions.assertThrows(InvalidVideoRoomTitleException.class, () -> new VideoRoomTitle(" "));
    }
}