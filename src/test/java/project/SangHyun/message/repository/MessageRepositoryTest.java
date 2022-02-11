package project.SangHyun.message.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.message.domain.Message;
import project.SangHyun.message.repository.impl.MessageDto;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MessageRepositoryTest {
    Member testMemberA;
    Member testMemberB;
    Member testMemberC;
    Member testMemberD;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        Member memberA = new Member("xptmxm6!", passwordEncoder.encode("xptmxm6!"), "길쌍", Department.CSE, "profileImgUrl", MemberRole.ROLE_MEMBER, "길쌍입니다.");
        testMemberA = memberRepository.save(memberA);

        Member memberB = new Member("xptmxm7!", passwordEncoder.encode("xptmxm7!"), "상현", Department.CSE, "profileImgUrl", MemberRole.ROLE_MEMBER, "상현입니다.");
        testMemberB = memberRepository.save(memberB);

        Member memberC = new Member("xptmxm8!", passwordEncoder.encode("xptmxm8!"), "현상", Department.CSE, "profileImgUrl", MemberRole.ROLE_MEMBER, "현상입니다.");
        testMemberC = memberRepository.save(memberC);

        Member memberD = new Member("xptmxm9!", passwordEncoder.encode("xptmxm9!"), "현상길", Department.CSE, "profileImgUrl", MemberRole.ROLE_MEMBER, "현상길입니다.");
        testMemberD = memberRepository.save(memberD);

    }

    @Test
    @DisplayName("보낸 회원들을 쪽지를 보낸 순으로 정렬하여 반환한다.")
    public void findSender() throws Exception {
        //given
        Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageA);
        Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageB);

        //when
        List<MessageDto> ActualResult = messageRepository.findAllCommunicatorsWithRecentMessageDescById(testMemberA.getId());

        //then
        Assertions.assertEquals(2,ActualResult.size());
        Assertions.assertEquals("두 번째 메세지 전송입니다.", ActualResult.get(0).getContent());
        Assertions.assertEquals("첫 번째 메세지 전송입니다.", ActualResult.get(1).getContent());
    }

    @Test
    @DisplayName("보낸 회원들의 쪽지가 여러 개인 경우 최신의 것을 기준으로 정렬하여 반환한다. (1:N)")
    public void findSender2() throws Exception {
        //given
        Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageA);
        Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageB);
        Message messageC = new Message("세 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageC);
        Message messageD = new Message("네 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageD);
        Message messageE = new Message("다섯 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageE);
        Message messageF = new Message("여섯 번째 메세지 전송입니다.", testMemberA, testMemberC, false, false, false);
        messageRepository.save(messageF);
        Message messageG = new Message("일곱 번째 메세지 전송입니다.", testMemberA, testMemberD, false, false, false);
        messageRepository.save(messageG);

        //when
        List<MessageDto> ActualResult = messageRepository.findAllCommunicatorsWithRecentMessageDescById(testMemberA.getId());

        //then
        Assertions.assertEquals(3, ActualResult.size());
        Assertions.assertEquals("일곱 번째 메세지 전송입니다.", ActualResult.get(0).getContent());
        Assertions.assertEquals("여섯 번째 메세지 전송입니다.", ActualResult.get(1).getContent());
        Assertions.assertEquals("세 번째 메세지 전송입니다.", ActualResult.get(2).getContent());
    }

    @Test
    @DisplayName("보낸 회원들의 쪽지가 여러 개인 경우 최신의 것을 기준으로 정렬하여 반환한다. (1:1)")
    public void findAll() throws Exception {
        //given
        Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageA);
        Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageB);
        Message messageC = new Message("세 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageC);
        Message messageD = new Message("네 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageD);
        Message messageE = new Message("다섯 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageE);
        Message messageF = new Message("여섯 번째 메세지 전송입니다.", testMemberA, testMemberB, false, false, false);
        messageRepository.save(messageF);
        Message messageG = new Message("일곱 번째 메세지 전송입니다.", testMemberA, testMemberC, false, false, false);
        messageRepository.save(messageG);

        //when
        List<Message> ActualResultA = messageRepository.findAllMessagesWithSenderIdAndReceiverIdDescById(testMemberB.getId(), testMemberA.getId());
        List<Message> ActualResultB = messageRepository.findAllMessagesWithSenderIdAndReceiverIdDescById(testMemberC.getId(), testMemberA.getId());

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
    @DisplayName("메세지의 내용을 통해 메세지를 검색한다.")
    public void findByContent() throws Exception {
        //given
        Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageA);
        Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageB);
        Message messageC = new Message("세 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageC);
        Message messageD = new Message("네 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageD);
        Message messageE = new Message("다섯 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageE);
        Message messageF = new Message("여섯 번째 메세지 전송입니다.", testMemberA, testMemberB, false, false, false);
        messageRepository.save(messageF);
        Message messageG = new Message("일곱 번째 메세지 전송입니다.", testMemberA, testMemberC, false, false, false);
        messageRepository.save(messageG);

        //when
        List<Message> messages = messageRepository.findAllMessagesByContent("첫 번째");

        //then
        Assertions.assertEquals(1, messages.size());
        Assertions.assertEquals("첫 번째 메세지 전송입니다.", messages.get(0).getContent());
    }

    @Test
    @DisplayName("받은 메세지들 중 안읽은 메세지의 개수를 출력한다.")
    public void countUnReadMessage() throws Exception {
        //given
        Message messageA = new Message("첫 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageA);
        Message messageB = new Message("두 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageB);
        Message messageC = new Message("세 번째 메세지 전송입니다.", testMemberB, testMemberA, false, false, false);
        messageRepository.save(messageC);
        Message messageD = new Message("네 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageD);
        Message messageE = new Message("다섯 번째 메세지 전송입니다.", testMemberC, testMemberA, false, false, false);
        messageRepository.save(messageE);
        Message messageF = new Message("여섯 번째 메세지 전송입니다.", testMemberA, testMemberB, false, false, false);
        messageRepository.save(messageF);
        Message messageG = new Message("일곱 번째 메세지 전송입니다.", testMemberA, testMemberC, false, false, false);
        messageRepository.save(messageG);

        //when
        Long count = messageRepository.countAllUnReadMessages(testMemberA.getId());

        //then
        Assertions.assertEquals(5, count);
    }
}