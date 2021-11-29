package project.SangHyun.domain.dto;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
public class MemberRegisterResponseDto {
    @ApiParam(value = "로그인 PK", required = true)
    private Long id;
    @ApiParam(value = "로그인 아이디", required = true)
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
