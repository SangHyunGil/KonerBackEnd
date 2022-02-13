package project.SangHyun.member.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.config.redis.RedisKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "메일 검증 요청")
public class VerifyRequestDto {

    @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요.", required = true, example = "GilSSang")
    private String email;

    @ApiModelProperty(value = "인증번호", notes = "인증 번호를 입력해주세요.", required = true, example = "-")
    private String authCode;

    @ApiModelProperty(value = "Redis 키", notes = "Redis 키를 입력해주세요.", required = true, example = "-")
    private RedisKey redisKey;
}
