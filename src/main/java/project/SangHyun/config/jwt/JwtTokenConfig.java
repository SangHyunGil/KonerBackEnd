package project.SangHyun.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtTokenConfig {

    private final JwtTokenHandler jwtTokenHandler;

    @Bean
    public JwtTokenHelper accessTokenHelper(@Value("${spring.jwt.key.access}") String secretKey,
                                            @Value("${spring.jwt.time.refresh}") Long validTime) {
        return new JwtTokenHelper(jwtTokenHandler, secretKey, validTime);
    }

    @Bean
    public JwtTokenHelper refreshTokenHelper(@Value("${spring.jwt.key.access}") String secretKey,
                                             @Value("${spring.jwt.time.refresh}") Long validTime) {
        return new JwtTokenHelper(jwtTokenHandler, secretKey, validTime);
    }
}
