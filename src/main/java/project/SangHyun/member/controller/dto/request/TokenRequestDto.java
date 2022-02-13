package project.SangHyun.member.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "재발행 요청")
public class TokenRequestDto {

    @ApiModelProperty(value = "Refresh Token", notes = "전달받았던 Refresh Token을 입력해주세요.", required = true)
    private String refreshToken;
}
