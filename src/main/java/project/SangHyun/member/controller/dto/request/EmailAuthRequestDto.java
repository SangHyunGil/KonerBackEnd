package project.SangHyun.member.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.config.redis.RedisKey;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "이메일 인증 요청")
public class EmailAuthRequestDto {

    @ApiModelProperty(value = "이메일", notes = "이메일을 입력해주세요", required = true, example = "member@email.com")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @ApiModelProperty(value = "Redis 키", notes = "Redis에 저장된 이메일 키를 입력해주세요", required = true, example = "-")
    @NotBlank(message = "Redis 키를 입력해주세요.")
    private RedisKey redisKey;
}
