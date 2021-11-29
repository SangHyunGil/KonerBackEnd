package project.SangHyun.domain.rediskey;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RedisKey {
    REFRESH("REFRESH"), VERIFY("VERIFY"),
    PASSWORD("PASSWORD"), UNKNOWN("UNKNOWN");

    private String key;

    RedisKey(String key) {
        this.key = key;
    }

    @JsonCreator
    public static RedisKey fromRedisKey(String value) {
        return Arrays.stream(RedisKey.values())
                .filter(v -> v.getKey().equals(value))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
