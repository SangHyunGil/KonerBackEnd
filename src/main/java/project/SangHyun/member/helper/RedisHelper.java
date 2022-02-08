package project.SangHyun.member.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisHelper {

    private final RedisTemplate redisTemplate;

    public void store(String key, String value, Long time) {
        setDataWithExpiration(key, value, time);
    }

    public Boolean validate(String key, String expectValue) {
        String value = getData(key);
        return value != null && value.equals(expectValue);
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
