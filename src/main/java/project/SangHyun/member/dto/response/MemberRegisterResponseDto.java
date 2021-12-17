package project.SangHyun.member.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원가입 요청 결과")
public class MemberRegisterResponseDto {
    @ApiModelProperty(value = "회원 ID(PK)")
    private Long id;
    @ApiModelProperty(value = "아이디")
    private String email;

    public static MemberRegisterResponseDto create(Member member) {
        return new MemberRegisterResponseDto(member.getId(), member.getEmail());
    }
}
