package project.SangHyun.dto.request.member;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "재발행 요청")
public class ReIssueRequestDto {
    private String refreshToken;
}
