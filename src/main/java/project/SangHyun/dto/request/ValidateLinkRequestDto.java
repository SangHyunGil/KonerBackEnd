package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.config.redis.RedisKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateLinkRequestDto {
    private String email;
    private String authCode;
    private RedisKey redisKey;
}
