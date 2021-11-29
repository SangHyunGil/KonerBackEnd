package project.SangHyun.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateLinkResponseDto {
    private Long id;
    private String email;
    private String authCode;
}
