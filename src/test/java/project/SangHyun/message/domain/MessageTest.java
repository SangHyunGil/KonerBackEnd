package project.SangHyun.message.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.member.domain.Member;

public class MessageTest {
    Member memberA;
    Member memberB;

    @BeforeEach
    public void init() {
        memberA = new Member(1L);
        memberB = new Member(2L);
    }

    @Test
    @DisplayName("쪽지를 전송한 사람이 쪽지를 제거한다.")
    public void deleteBySender() throws Exception {
        //given
        Message message = new Message("테스트 쪽지입니다.", memberA, memberB, false, false);

        //when
        message.deleteBySender();

        //then
        Assertions.assertEquals(false, message.isDeletable());
        Assertions.assertEquals(true, message.isDeletedBySender());
    }

    @Test
    @DisplayName("쪽지를 수신한 사람이 쪽지를 제거한다.")
    public void deleteByReceiver() throws Exception {
        //given
        Message message = new Message("테스트 쪽지입니다.", memberA, memberB, false, false);

        //when
        message.deleteByReceiver();

        //then
        Assertions.assertEquals(false, message.isDeletable());
        Assertions.assertEquals(true, message.isDeletedByReceiver());
    }

    @Test
    @DisplayName("송신자와 수신자에 의해 제거된 쪽지는 지워질 수 있다.")
    public void isDeletable() throws Exception {
        //given
        Message message = new Message("테스트 쪽지입니다.", memberA, memberB, false, false);

        //when
        message.deleteBySender();
        message.deleteByReceiver();

        //then
        Assertions.assertEquals(true, message.isDeletable());
        Assertions.assertEquals(true, message.isDeletedByReceiver());
        Assertions.assertEquals(true, message.isDeletedBySender());
    }
}
