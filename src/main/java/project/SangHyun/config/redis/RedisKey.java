package project.SangHyun.config.redis;

import lombok.Getter;

@Getter
public enum RedisKey {
    REFRESH("REFRESH"), VERIFY("VERIFY"),
    PASSWORD("PASSWORD"), UNKNOWN("UNKNOWN");

    private String key;

    RedisKey(String key) {
        this.key = key;
    }
}
