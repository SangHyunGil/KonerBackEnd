package project.SangHyun.config.redis;

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
}
