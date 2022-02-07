package project.SangHyun.member.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.SangHyun.common.helper.RedisHelper;

@Getter
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisService {

    private final RedisHelper redisHelper;

    public void store(String key, String value, Long time) {
        redisHelper.setDataWithExpiration(key, value, time);
    }

    public void delete(String key) {
        redisHelper.deleteData(key);
    }

    public Boolean validate(String key, String expectValue) {
        String value = redisHelper.getData(key);
        return value != null && value.equals(expectValue);
    }
}
