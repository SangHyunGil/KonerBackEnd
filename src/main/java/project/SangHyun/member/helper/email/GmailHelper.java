package project.SangHyun.member.helper.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import project.SangHyun.config.redis.RedisKey;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static project.SangHyun.member.helper.email.HtmlFactory.getHtml;

@Component
@RequiredArgsConstructor
public class GmailHelper implements EmailHelper{

    private static final String UNIVERSITY_DOMAIN = "@koreatech.ac.kr";
    private static final String VERIFY_URL = "http://koner.kr/signup/verify";

    private final JavaMailSender javaMailSender;
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
