package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailRequestDto {
    private String email;
    private String authCode;
    private String redisKey;
}
