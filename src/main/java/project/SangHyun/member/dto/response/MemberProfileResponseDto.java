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

    public static MemberProfileResponseDto create(Member member) {
        return new MemberProfileResponseDto(member.getId(), member.getEmail(),
                member.getNickname(), member.getDepartment());
    }
}