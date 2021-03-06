package project.SangHyun.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.SangHyun.factory.message.MessageFactory;
import project.SangHyun.member.domain.Member;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.service.dto.request.MessageCreateDto;
import project.SangHyun.message.service.dto.response.FindCommunicatorsDto;
import project.SangHyun.message.service.dto.response.MessageDto;

import java.util.List;

public class MessageServiceIntegrationTest extends ServiceIntegrationTest {

    @Test
    @DisplayName("쪽지를 송신한다.")
    public void createMessage() throws Exception {
        //given
        Member sender = studyMember;
        Member receiver = webAdminMember;
        MessageCreateDto requestDto = MessageFactory.makeCreateDto(null, sender, receiver);

        //when
        MessageDto ActualResult = messageService.createMessage(requestDto);

        //then
        Assertions.assertEquals("테스트 쪽지입니다.", ActualResult.getContent());
    }

    @Test
    @DisplayName("쪽지를 나눈 대화 상대 리스트를 최신순으로 정렬하여 출력한다.")
    public void findAllCommunicatorsWithRecentMessage() throws Exception {
        //given
        Member receiver = studyMember;

        //when
        List<FindCommunicatorsDto> ActualResult = messageService.findAllCommunicatorsWithRecentMessage(receiver.getId());

        //then
        Assertions.assertEquals(2, ActualResult.size());
        Assertions.assertEquals("일곱 번째 메세지 전송입니다.", ActualResult.get(0).getContent());
        Assertions.assertEquals("세 번째 메세지 전송입니다.", ActualResult.get(1).getContent());
    }

    @Test
    @DisplayName("어떤 상대와 쪽지를 나눈 모든 쪽지를 최신순으로 정렬하여 출력한다.")
    public void findAllMessages() throws Exception {
        //given
        Member receiver = studyMember;
        Member sender = hasNoResourceMember;

        //when
        List<MessageDto> ActualResult = messageService.findAllMessages(sender.getId(), receiver.getId());

        //then
        Assertions.assertEquals(4, ActualResult.size());
        Assertions.assertEquals("일곱 번째 메세지 전송입니다.", ActualResult.get(0).getContent());
        Assertions.assertEquals("다섯 번째 메세지 전송입니다.", ActualResult.get(1).getContent());
        Assertions.assertEquals("네 번째 메세지 전송입니다.", ActualResult.get(2).getContent());
        Assertions.assertEquals("두 번째 메세지 전송입니다.", ActualResult.get(3).getContent());
    }

    @Test
    @DisplayName("어떤 상대와 쪽지를 나눈 모든 쪽지를 조회한다면 모두 읽음 처리된다.")
    public void findAllMessages2() throws Exception {
        //given
        Member receiver = studyMember;
        Member sender = hasNoResourceMember;

        //when
        messageService.findAllMessages(sender.getId(), receiver.getId());
        persistenceContextClear();

        List<Message> ActualResult = messageRepository.findAllMessagesWithSenderIdAndReceiverIdDescById(sender.getId(), receiver.getId());

        //then
        Assertions.assertEquals(4, ActualResult.size());
        Assertions.assertEquals(false, ActualResult.get(0).getIsRead());
        Assertions.assertEquals(true, ActualResult.get(1).getIsRead());
        Assertions.assertEquals(true, ActualResult.get(2).getIsRead());
        Assertions.assertEquals(true, ActualResult.get(3).getIsRead());
    }

    @Test
    @DisplayName("송신자에 의해 쪽지가 제거된다.")
    public void deleteBySender() throws Exception {
        //given
        Message message = messageRepository.findAllMessagesByContent("첫 번째").get(0);

        //when
        messageService.deleteBySender(message.getId());

        int size = messageRepository.findAllMessagesByContent("첫 번째").size();

        //then
        Assertions.assertEquals(true, message.isDeletedBySender());
        Assertions.assertEquals(false, message.isDeletedByReceiver());
        Assertions.assertEquals(1, size);
    }

    @Test
    @DisplayName("수신자에 의해 쪽지가 제거된다.")
    public void deleteByReceiver() throws Exception {
        //given
        Message message = messageRepository.findAllMessagesByContent("첫 번째").get(0);

        //when
        messageService.deleteByReceiver(message.getId());
        persistenceContextClear();

        int size = messageRepository.findAllMessagesByContent("첫 번째").size();

        //then
        Assertions.assertEquals(false, message.isDeletedBySender());
        Assertions.assertEquals(true, message.isDeletedByReceiver());
        Assertions.assertEquals(1, size);
    }

    @Test
    @DisplayName("모두에 의해 쪽지가 제거된다.")
    public void deleteByAll() throws Exception {
        //given
        Message message = messageRepository.findAllMessagesByContent("첫 번째").get(0);

        //when
        messageService.deleteByReceiver(message.getId());
        messageService.deleteBySender(message.getId());
        persistenceContextClear();

        int size = messageRepository.findAllMessagesByContent("첫 번째").size();

        //then
        Assertions.assertEquals(true, message.isDeletedBySender());
        Assertions.assertEquals(true, message.isDeletedByReceiver());
        Assertions.assertEquals(0, size);
    }

    @Test
    @DisplayName("받은 메세지들 중 안읽은 메세지의 개수를 출력한다.")
    public void countUnReadMessages() throws Exception {
        //given
        Member receiver = studyMember;

        //when
        Long count = messageService.countUnReadMessages(receiver.getId());

        //then
        Assertions.assertEquals(5, count);
    }

    private void persistenceContextClear() {
        em.flush();
        em.clear();
    }
}
