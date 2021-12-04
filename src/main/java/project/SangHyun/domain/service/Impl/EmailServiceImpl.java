package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import project.SangHyun.config.redis.RedisKey;
import project.SangHyun.domain.service.EmailService;

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
    public SimpleMailMessage makeMail(String email, String value, String redisKey) {
        SimpleMailMessage smm = new SimpleMailMessage();

        if (redisKey.equals(RedisKey.VERIFY.getKey())) {
            smm.setTo(email + UNIVERSITY_EMAIL);
            smm.setSubject("회원가입 이메일 인증");
            smm.setText("http://localhost:3000/signup/verify?email="+email+"&authCode="+value+"&redisKey=VERIFY");
        } else {
            smm.setTo(email + UNIVERSITY_EMAIL);
            smm.setSubject("비밀번호 변경");
            smm.setText("http://localhost:3000/signup/password?email="+email+"&authCode="+value+"&redisKey=PASSWORD");
        }

        return smm;
    }
}
