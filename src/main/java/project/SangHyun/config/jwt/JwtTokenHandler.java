package project.SangHyun.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.SangHyun.common.advice.exception.AuthenticationEntryPointException;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenHandler {

    public String createToken(String secretKey, Long validTime, String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractSubject(String secretKey, String token) {
        try {
            return parseJwt(secretKey, token).getBody().getSubject();
        } catch(Exception e) {
            throw new AuthenticationEntryPointException();
        }
    }

    public boolean validateToken(String secretKey, String token) {
        try {
            parseJwt(secretKey, token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Jws<Claims> parseJwt(String secretKey, String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }
}
