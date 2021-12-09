package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value = "프로필 요청 결과")
public class MemberProfileResponseDto {
    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "아이디")
    private String email;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "학과")
    private String department;

    public static MemberProfileResponseDto createDto(Member member) {
        return MemberProfileResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .build();
    }

    @Builder
    public MemberProfileResponseDto(Long memberId, String email, String nickname, String department) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
    }
}
