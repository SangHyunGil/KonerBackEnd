package project.SangHyun.domain.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.config.security.jwt.JwtTokenProvider;
import project.SangHyun.domain.service.RedisService;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;

    @Override
    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setDataWithExpiration(String key, String value, Long time) {
        if (this.getData(key) != null)
            this.deleteData(key);
        Duration expireDuration = Duration.ofSeconds(time);
        redisTemplate.opsForValue().set(key, value, expireDuration);
    }

    @Override
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

}
