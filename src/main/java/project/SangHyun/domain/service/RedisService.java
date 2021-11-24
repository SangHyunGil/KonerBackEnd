package project.SangHyun.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.config.security.jwt.JwtTokenProvider;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void setDataWithExpiration(String key, String value, Long time) {
        if (this.getData(key) != null)
            this.deleteData(key);
        Duration expireDuration = Duration.ofSeconds(time);
        redisTemplate.opsForValue().set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

}
