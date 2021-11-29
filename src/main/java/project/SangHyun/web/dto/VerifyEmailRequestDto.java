package project.SangHyun.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.rediskey.RedisKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailRequestDto {
    String email;
    String authCode;
    String redisKey;
}
