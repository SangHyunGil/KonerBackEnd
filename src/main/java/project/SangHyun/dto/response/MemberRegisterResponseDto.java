package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
@ApiModel(value = "회원가입 요청 결과")
public class MemberRegisterResponseDto {
    @ApiModelProperty(value = "회원 ID(PK)")
    private Long id;
    @ApiModelProperty(value = "아이디")
    private String email;

    public static MemberRegisterResponseDto createDto(Member member) {
        return MemberRegisterResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }

    @Builder
    public MemberRegisterResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
