package project.SangHyun.config.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

class JwtTokenHandlerTest {

    JwtTokenHandler jwtTokenHandler = new JwtTokenHandler();

    @Test
    void createTokenTest() {
        // given, when
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // then
        Assertions.assertEquals(String.class, token.getClass());
    }

    @Test
    void extractSubjectTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String subject = "subject";
        String token = createToken(encodedKey, subject, 60L * 100);

        // when
        String extractedSubject = jwtTokenHandler.extractSubject(encodedKey, token);

        // then
        Assertions.assertEquals(extractedSubject, subject);
    }

    @Test
    void validateTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L * 100);

        // when
        boolean isValid = jwtTokenHandler.validateToken(encodedKey, token);

        // then
        Assertions.assertEquals(true, isValid);
    }

    @Test
    void invalidateByInvalidKeyTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // when
        boolean isValid = jwtTokenHandler.validateToken("invalid", token);

        // then
        Assertions.assertEquals(false, isValid);
    }

    @Test
    void invalidateByExpiredTokenTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("myKey".getBytes());
        String token = createToken(encodedKey, "subject", 0L);

        // when
        boolean isValid = jwtTokenHandler.validateToken(encodedKey, token);

        // then
        Assertions.assertEquals(false, isValid);
    }

    private String createToken(String encodedKey, String subject, long maxAgeSeconds) {
        return jwtTokenHandler.createToken(
                encodedKey,
                maxAgeSeconds,
                subject);
    }
}