package project.SangHyun.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import project.SangHyun.domain.rediskey.RedisKey;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private static final String UNIVERSITY_EMAIL = "@koreatech.ac.kr";

    @Async
    public void send(String email, String value, String redisKey) {
        SimpleMailMessage smm = makeMail(email, value, redisKey);
        javaMailSender.send(smm);
    }

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
