package project.SangHyun.member.dto.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "아이디")
    private String email;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "학과")
    private String department;

    @ApiModelProperty(value = "프로필 사진")
    private String profileImgUrl;

    public static MemberResponseDto create(Member member) {
        return new MemberResponseDto(member.getId(), member.getEmail(),
                member.getNickname(), member.getDepartmentName(), member.getProfileImgUrl());
    }
}
