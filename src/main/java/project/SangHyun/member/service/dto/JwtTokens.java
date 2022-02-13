package project.SangHyun.member.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokens {

    private String accessToken;
    private String refreshToken;
}
