package project.SangHyun.member.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.TestDB;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.dto.request.MemberEmailAuthRequestDto;
import project.SangHyun.member.service.EmailService;
import project.SangHyun.member.tools.sign.SignFactory;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EmailServiceIntegrationTest {

    @Autowired
    EmailService emailService;

    @Autowired
    TestDB testDB;

    @BeforeEach
    void beforeEach() {
        testDB.init();
    }

    @Test
    @DisplayName("회원가입 후 인증을 위한 검증 메일을 전송한다.")
    public void sendMail_register() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.VERIFY);

        //when
        String ActualResult = emailService.send(requestDto.getEmail(), requestDto.getRedisKey());

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }

    @Test
    @DisplayName("비밀번호 변경을 위한 검증 메일을 전송한다.")
    public void sendMail_pw() throws Exception {
        //given
        MemberEmailAuthRequestDto requestDto = SignFactory.makeEmailAuthRequestDto(RedisKey.PASSWORD);

        //when
        String ActualResult = emailService.send(requestDto.getEmail(), requestDto.getRedisKey());

        //then
        Assertions.assertEquals("이메일 전송에 성공하였습니다.", ActualResult);
    }
}
