package project.SangHyun.config.jwt;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenHelper {

    private final JwtTokenHandler jwtTokenHandler;
    private final String secretKey;
    private final Long validTime;

    public String createToken(String subject) {
        return jwtTokenHandler.createToken(secretKey, validTime, subject);
    }

    public String extractSubject(String token) {
        return jwtTokenHandler.extractSubject(secretKey, token);
    }

    public Boolean validateToken(String token) {
        return jwtTokenHandler.validateToken(secretKey, token);
    }

    public Long getValidTime() {
        return validTime;
    }
}
