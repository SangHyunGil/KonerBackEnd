package project.SangHyun.utils.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.utils.service.EmailService;

import java.util.Arrays;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private static final String UNIVERSITY_EMAIL = "@koreatech.ac.kr";

    @Override
    @Async
    public void send(String email, String value, String redisKey) {
        SimpleMailMessage smm = makeMail(email, value, redisKey);
        javaMailSender.send(smm);
    }

    @Override
    public SimpleMailMessage makeMail(String email, String value, String key) {
        SimpleMailMessage smm = new SimpleMailMessage();
        RedisKey redisKey = RedisKey.distinguish(key);
        smm.setTo(email + UNIVERSITY_EMAIL);
        smm.setSubject(getTitle(redisKey));
        smm.setText("http://localhost:3000/signup/verify?email=" + email + "&authCode=" + redisKey.getKey());

        return smm;
    }

    private String getTitle(RedisKey redis) {
        return redis.equals(RedisKey.VERIFY) ? "회원가입 인증 요청" : "비밀번호 변경 요청";
    }
}
