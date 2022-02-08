package project.SangHyun.member.service.unit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.dto.request.MemberEmailAuthRequestDto;
import project.SangHyun.member.helper.HtmlFactory;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.impl.GmailServiceImpl;
import project.SangHyun.member.tools.sign.SignFactory;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class EmailServiceUnitTest {

    Member authMember;
    Member notAuthMember;
    MockedStatic<HtmlFactory> htmlFactoryMockedStatic;

    @InjectMocks
    GmailServiceImpl emailService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    RedisHelper redisHelper;
    @Mock
    JavaMailSender javaMailSender;

    @BeforeEach
    public void init() {
        authMember = SignFactory.makeAuthTestMember();
        notAuthMember = SignFactory.makeTestNotAuthMember();

        htmlFactoryMockedStatic = mockStatic(HtmlFactory.class);
    }

    @AfterEach
    public void end() {
        htmlFactoryMockedStatic.close();
    }

    @Test
    @DisplayName("회원가입 후 인증을 위한 이메일을 전송한다.")
    public void sendMail_register() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.VERIFY);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(notAuthMember));
        given(javaMailSender.createMimeMessage()).willReturn(new MimeMessage((Session)null));
        given(HtmlFactory.getHtml(any())).willReturn("html");
        willDoNothing().given(redisHelper).store(any(), any(), any());
        willDoNothing().given(javaMailSender).send(any(MimeMessage.class));

        //when
        String ActualResult = emailService.send(requestDto.getEmail(), requestDto.getRedisKey());

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    @DisplayName("비밀번호 변경을 위한 이메일을 전송한다.")
    public void sendMail_pw() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.PASSWORD);

        //mocking
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(authMember));
        given(javaMailSender.createMimeMessage()).willReturn(new MimeMessage((Session)null));
        given(HtmlFactory.getHtml(any())).willReturn("html");
        willDoNothing().given(redisHelper).store(any(), any(), any());
        willDoNothing().given(javaMailSender).send(any(MimeMessage.class));

        //when
        String ActualResult = emailService.send(requestDto.getEmail(), requestDto.getRedisKey());

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }
}
