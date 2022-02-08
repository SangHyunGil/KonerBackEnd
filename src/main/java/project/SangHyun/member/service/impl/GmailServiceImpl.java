package project.SangHyun.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.advice.exception.MemberNotFoundException;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.helper.RedisHelper;
import project.SangHyun.member.repository.MemberRepository;
import project.SangHyun.member.service.EmailService;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

import static project.SangHyun.member.helper.HtmlFactory.getHtml;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GmailServiceImpl implements EmailService {

    private static final String UNIVERSITY_DOMAIN = "@koreatech.ac.kr";
    private static final String VERIFY_URL = "http://koner.kr/signup/verify";
    private static final Long EMAIL_VALID_TIME = 60 * 5L;

    private final RedisHelper redisHelper;
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    public String send(String email, RedisKey redisKey) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        String authCode = makeAuthCodeAndStoreInRedis(redisKey, email);
        sendEmail(member.getEmail(), authCode, redisKey);
        return "이메일 전송에 성공하였습니다.";
    }

    private String makeAuthCodeAndStoreInRedis(RedisKey redisKey, String email) {
        String key = getRedisKey(redisKey, email);
        String authCode = UUID.randomUUID().toString();
        redisHelper.store(key, authCode, EMAIL_VALID_TIME);
        return authCode;
    }

    private String getRedisKey(RedisKey redisKey, String email) {
        return redisKey.getKey() + email;
    }

    @Async
    public void sendEmail(String email, String value, RedisKey redisKey) {
        MimeMessage mm = makeMail(email, value, redisKey);
        javaMailSender.send(mm);
    }

    private MimeMessage makeMail(String email, String authCode, RedisKey key) {
        try {
            MimeMessage mm = javaMailSender.createMimeMessage();
            mm.setFrom(new InternetAddress("zizon5941@gmail.com"));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email+ UNIVERSITY_DOMAIN));
            mm.setSubject(makeTitle(key), "UTF-8");
            mm.setText(getHtml(getUrl(email, authCode, key)), "UTF-8", "html");

            return mm;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Mail Send Failed!");
        }
    }

    private String makeTitle(RedisKey key) {
        return RedisKey.isVerifying(key) ? "회원가입 인증 요청" : "비밀번호 변경 요청";
    }

    private String getUrl(String email, String authCode, RedisKey key) {
        return VERIFY_URL + makeParam(email, authCode, key);
    }

    private String makeParam(String email, String authCode, RedisKey key) {
        return "?email=" + email + "&authCode=" + authCode + "&redisKey=" + key.getKey();
    }
}
