package project.SangHyun.member.service;

import project.SangHyun.config.redis.RedisKey;

public interface EmailService {
    String send(String email, RedisKey redisKey);
}
