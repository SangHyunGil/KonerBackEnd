package project.SangHyun.message.service.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.SangHyun.member.domain.Member;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.MessageRepository;
import project.SangHyun.message.repository.impl.RecentMessageDto;
import project.SangHyun.message.service.MessageService;
import project.SangHyun.message.service.dto.request.MessageCreateDto;
import project.SangHyun.message.service.dto.response.FindCommunicatorsDto;
import project.SangHyun.message.service.dto.response.MessageDto;
import project.SangHyun.message.tools.MessageFactory;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class MessageServiceUnitTest {
    @InjectMocks
    MessageService messageService;
    @Mock
    MessageRepository messageRepository;

    @Test
    @DisplayName("쪽지를 송신한다.")
    public void createMessage() throws Exception {
        //given
        Member memberA = MessageFactory.makeTestAuthMember();
        Member memberB = MessageFactory.makeTestAdminMember();
        MessageCreateDto requestDto = MessageFactory.makeCreateDto(1L, memberA, memberB);
        Message message = requestDto.toEntity();
        MessageDto ExpectResult = MessageFactory.makeMessageDto(message);

        //mocking
        given(messageRepository.save(any())).willReturn(message);

        //when
        MessageDto ActualResult = messageService.createMessage(requestDto);

        //then
        Assertions.assertEquals(ExpectResult.getContent(), ActualResult.getContent());
    }

    @Test
    @DisplayName("쪽지를 나눈 대화 상대 리스트를 최신순으로 정렬하여 출력한다.")
    public void findAllCommunicatorsWithRecentMessage() throws Exception {
        //given
        Member testMemberA = MessageFactory.makeTestAuthMember();
        Member testMemberB = MessageFactory.makeTestAuthMember2();
        Member testMemberC = MessageFactory.makeTestAdminMember();
        Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, true, false, false);
        Message messageC = new Message("세 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        Message messageD = new Message("네 번째 메세지 전송입니다.", testMemberC, testMemberA, true, false, false);
        Message messageE = new Message("다섯 번째 메세지 전송입니다.", testMemberC, testMemberA, true, false, false);
        Message messageF = new Message("여섯 번째 메세지 전송입니다.", testMemberA, testMemberC, false, false, false);

        RecentMessageDto recentMessageDtoC = new RecentMessageDto(messageC.getId(), messageC.getSender(), messageC.getReceiver(), messageC.getContent(), 2L);
        RecentMessageDto recentMessageDtoF = new RecentMessageDto(messageF.getId(), messageF.getSender(), messageF.getReceiver(), messageF.getContent(), 0L);

        //mocking
        given(messageRepository.findAllCommunicatorsWithRecentMessageDescById(testMemberA.getId())).willReturn(List.of(recentMessageDtoF, recentMessageDtoC));

        //when
        List<FindCommunicatorsDto> ActualResult = messageService.findAllCommunicatorsWithRecentMessage(testMemberA.getId());

        //then
        Assertions.assertEquals("여섯 번째 메세지 전송입니다.", ActualResult.get(0).getContent());
        Assertions.assertEquals("세 번째 메세지 전송입니다.", ActualResult.get(1).getContent());

        Assertions.assertEquals(0, ActualResult.get(0).getUnReadCount());
        Assertions.assertEquals(2, ActualResult.get(1).getUnReadCount());
    }

    @Test
    @DisplayName("어떤 상대와 쪽지를 나눈 모든 쪽지를 최신순으로 정렬하여 출력한다.")
    public void findAllMessages() throws Exception {
        //given
        Member testMemberA = MessageFactory.makeTestAuthMember();
        Member testMemberB = MessageFactory.makeTestAuthMember2();
        Member testMemberC = MessageFactory.makeTestAdminMember();
        Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        Message messageC = new Message("세 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        Message messageD = new Message("네 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        Message messageE = new Message("다섯 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        Message messageF = new Message("여섯 번째 메세지 전송입니다.", testMemberA, testMemberB, false, false, false);
        Message messageG = new Message("일곱 번째 메세지 전송입니다.", testMemberA, testMemberC, false, false, false);

        //mocking
        given(messageRepository.findAllMessagesWithSenderIdAndReceiverIdDescById(testMemberB.getId(), testMemberA.getId())).willReturn(List.of(messageF, messageC, messageA));
        given(messageRepository.findAllMessagesWithSenderIdAndReceiverIdDescById(testMemberC.getId(), testMemberA.getId())).willReturn(List.of(messageG, messageE, messageD, messageB));

        //when
        List<MessageDto> ActualResultA = messageService.findAllMessages(testMemberB.getId(), testMemberA.getId());
        List<MessageDto> ActualResultB = messageService.findAllMessages(testMemberC.getId(), testMemberA.getId());

        //then
        Assertions.assertEquals(3, ActualResultA.size());
        Assertions.assertEquals("여섯 번째 메세지 전송입니다.", ActualResultA.get(0).getContent());
        Assertions.assertEquals("세 번째 메세지 전송입니다.", ActualResultA.get(1).getContent());
        Assertions.assertEquals("첫 번째 메세지 전송입니다.", ActualResultA.get(2).getContent());

        Assertions.assertEquals(4, ActualResultB.size());
        Assertions.assertEquals("일곱 번째 메세지 전송입니다.", ActualResultB.get(0).getContent());
        Assertions.assertEquals("다섯 번째 메세지 전송입니다.", ActualResultB.get(1).getContent());
        Assertions.assertEquals("네 번째 메세지 전송입니다.", ActualResultB.get(2).getContent());
        Assertions.assertEquals("두 번째 메세지 전송입니다.", ActualResultB.get(3).getContent());
    }

    @Test
    @DisplayName("송신자에 의해 쪽지가 제거된다.")
    public void deleteBySender() throws Exception {
        //given
        Member testMemberA = MessageFactory.makeTestAuthMember();
        Member testMemberB = MessageFactory.makeTestAdminMember();
        Message message = MessageFactory.makeTestMessage(1L, testMemberA, testMemberB);

        //mocking
        given(messageRepository.findById(message.getId())).willReturn(java.util.Optional.of(message));

        //when
        messageService.deleteBySender(message.getId());

        //then
        Assertions.assertEquals(false, message.isDeletable());
        Assertions.assertEquals(true, message.isDeletedBySender());
    }

    @Test
    @DisplayName("수신자에 의해 쪽지가 제거된다.")
    public void deleteByReceiver() throws Exception {
        //given
        Member testMemberA = MessageFactory.makeTestAuthMember();
        Member testMemberB = MessageFactory.makeTestAdminMember();
        Message message = MessageFactory.makeTestMessage(1L, testMemberA, testMemberB);

        //mocking
        given(messageRepository.findById(message.getId())).willReturn(java.util.Optional.of(message));

        //when
        messageService.deleteByReceiver(message.getId());

        //then
        Assertions.assertEquals(false, message.isDeletable());
        Assertions.assertEquals(true, message.isDeletedByReceiver());
    }

    @Test
    @DisplayName("모두에 의해 쪽지가 제거된다.")
    public void deleteByAll() throws Exception {
        //given
        Member testMemberA = MessageFactory.makeTestAuthMember();
        Member testMemberB = MessageFactory.makeTestAdminMember();
        Message message = MessageFactory.makeTestMessage(1L, testMemberA, testMemberB);

        //mocking
        given(messageRepository.findById(message.getId())).willReturn(java.util.Optional.of(message));
        willDoNothing().given(messageRepository).delete(message);

        //when
        messageService.deleteBySender(message.getId());
        messageService.deleteByReceiver(message.getId());

        //then
        Assertions.assertEquals(true, message.isDeletedBySender());
        Assertions.assertEquals(true, message.isDeletedByReceiver());
    }

    @Test
    @DisplayName("받은 메세지들 중 안읽은 메세지의 개수를 출력한다.")
    public void countUnReadMessages() throws Exception {
        //given
        Member testMemberA = MessageFactory.makeTestAuthMember();
        Member testMemberB = MessageFactory.makeTestAuthMember2();
        Member testMemberC = MessageFactory.makeTestAdminMember();
        Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        Message messageC = new Message("세 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        Message messageD = new Message("네 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        Message messageE = new Message("다섯 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        Message messageF = new Message("여섯 번째 메세지 전송입니다.", testMemberA, testMemberC, false, false, false);


        //mocking
        given(messageRepository.countAllUnReadMessages(testMemberA.getId())).willReturn(5L);

        //when
        Long count = messageService.countUnReadMessages(testMemberA.getId());

        //then
        Assertions.assertEquals(5L, count);
    }
}