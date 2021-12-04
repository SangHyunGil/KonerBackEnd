package project.SangHyun.domain.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void send(String email, String value, String redisKey);
    SimpleMailMessage makeMail(String email, String value, String redisKey);
}
