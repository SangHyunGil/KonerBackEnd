package project.SangHyun.member.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import project.SangHyun.common.advice.exception.RedisValueDifferentException;
import project.SangHyun.config.redis.RedisKey;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisHelper {

    private final RedisTemplate redisTemplate;

    public String getRedisKey(RedisKey redisKey, String object) {
        return redisKey.getKey() + object;
    }

    public void store(String key, String value, Long time) {
        setDataWithExpiration(key, value, time);
    }

    public void validate(String key, String expectValue) {
        String value = getData(key);
        if (value == null || !value.equals(expectValue)) {
            throw new RedisValueDifferentException();
        }
    }

    public void delete(String key) {
        deleteData(key);
    }

    private void setDataWithExpiration(String key, String value, Long time) {
        if (this.getData(key) != null)
            this.deleteData(key);
        Duration expireDuration = Duration.ofSeconds(time);
        redisTemplate.opsForValue().set(key, value, expireDuration);
    }

    private String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    private void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
