package project.SangHyun.member.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원 반환 서비스 계층 DTO")
public class MemberDto {

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

    @ApiModelProperty(value = "자기소개글글")
    private String introduction;

    public static MemberDto create(Member member) {
        return new MemberDto(member.getId(), member.getEmail(),
                member.getNickname(), member.getDepartmentName(), member.getProfileImgUrl(), member.getIntroduction());
    }
}
