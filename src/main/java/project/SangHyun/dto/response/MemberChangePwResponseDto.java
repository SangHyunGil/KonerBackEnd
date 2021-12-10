package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
@ApiModel(value = "회원 비밀번호 변경 요청 결과")
public class MemberChangePwResponseDto {
    @ApiModelProperty(value = "회원 ID(PK)")
    Long id;

    @ApiModelProperty(value = "아이디")
    String email;

    @ApiModelProperty(value = "비밀번호")
    String password;

    public static MemberChangePwResponseDto createDto(Member member) {
        return MemberChangePwResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }

    @Builder
    public MemberChangePwResponseDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
