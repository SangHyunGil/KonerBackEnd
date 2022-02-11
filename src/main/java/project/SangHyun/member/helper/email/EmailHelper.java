package project.SangHyun.member.helper.email;

import project.SangHyun.config.redis.RedisKey;

public interface EmailHelper {

    void sendEmail(String email, String value, RedisKey redisKey);
}
