package project.SangHyun.config.redis;

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

    public static RedisKey distinguish(String key) {
        return Arrays.stream(values())
                .filter(redisKey -> redisKey.getKey().equals(key))
                .findFirst()
                .orElse(RedisKey.UNKNOWN);
    }

    public static boolean isVerifying(RedisKey redisKey) {
        return redisKey.equals(VERIFY);
    }
}
