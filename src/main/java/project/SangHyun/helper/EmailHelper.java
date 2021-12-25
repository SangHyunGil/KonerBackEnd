package project.SangHyun.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import project.SangHyun.config.redis.RedisKey;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailHelper {
    private final JavaMailSender javaMailSender;
    private static final String UNIVERSITY_EMAIL = "@koreatech.ac.kr";
    private static final String VERIFY_URL = "http://localhost:3000/signup/verify";

    @Async
    public void send(String email, String value, String redisKey) {
        SimpleMailMessage smm = makeMail(email, value, redisKey);
        javaMailSender.send(smm);
    }

    public SimpleMailMessage makeMail(String email, String authCode, String key) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email + UNIVERSITY_EMAIL);
        smm.setSubject(makeTitle(key));
        smm.setText(VERIFY_URL + makeParam(email, authCode, key));
        return smm;
    }

    private String makeTitle(String key) {
        return RedisKey.isVerifying(key) ? "회원가입 인증 요청" : "비밀번호 변경 요청";
    }

    private String makeParam(String email, String authCode, String key) {
        return "?" + emailParam(email) + authCodeParam(authCode) + redisKeyParam(key);
    }

    private String emailParam(String email) {
        return "email=" + email;
    }

    private String authCodeParam(String authCode) {
        return "&authCode=" + authCode;
    }

    private String redisKeyParam(String key) {
        return "&redisKey=" + key;
    }
}
