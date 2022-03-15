package project.SangHyun.member.helper.email;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.SangHyun.config.redis.RedisKey;

@Component
@RequiredArgsConstructor
public class AwsSesEmailHelper implements EmailHelper {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private static final String FROM = "syu9710@gmail.com";
    private static final String UNIVERSITY_DOMAIN = "@koreatech.ac.kr";
    private static final String VERIFY_URL = "http://koner.kr/signup/verify";

    @Override
    public void sendEmail(String email, String value, RedisKey redisKey) {
        SendEmailRequest sendEmailRequest = new SendEmailRequest()
                .withDestination(
                        getReceiverEmail(email)
                )
                .withMessage(
                        getContent(email, value, redisKey))
                .withSource(FROM);

        amazonSimpleEmailService
                .sendEmail(sendEmailRequest);
    }

    private Message getContent(String email, String value, RedisKey redisKey) {
        return new Message()
                .withSubject(new Content()
                        .withCharset("UTF-8").withData(makeTitle(redisKey)))
                .withBody(new Body()
                        .withHtml(new Content()
                                .withCharset("UTF-8")
                                        .withData(HtmlFactory.getHtml(getUrl(email, value, redisKey)))));
    }

    private Destination getReceiverEmail(String email) {
        return new Destination().withToAddresses(getUniversityEmail(email));
    }

    private String getUniversityEmail(String email) {
        return email+UNIVERSITY_DOMAIN;
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
