package project.SangHyun.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberChangePwRequestDto {
    String email;
    String password;
    String authCode;
}
