package project.SangHyun.dto.response.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "메일 인증 링크 검증 요청 결과")
public class ValidateLinkResponseDto {
    @ApiModelProperty(value = "아이디")
    private Long id;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "인증코드")
    private String authCode;
}
