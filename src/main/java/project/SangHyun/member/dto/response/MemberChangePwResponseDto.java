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
@ApiModel(value = "회원 비밀번호 변경 요청 결과")
public class MemberChangePwResponseDto {
    @ApiModelProperty(value = "회원 ID(PK)")
    Long id;

    @ApiModelProperty(value = "아이디")
    String email;

    @ApiModelProperty(value = "비밀번호")
    String password;

    public static MemberChangePwResponseDto create(Member member) {
        return new MemberChangePwResponseDto(member.getId(), member.getEmail(), member.getPassword());
    }
}
