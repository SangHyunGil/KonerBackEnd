package project.SangHyun.member.controller.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.service.dto.response.MemberDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "회원 요청 결과")
public class MemberResponseDto {

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "아이디")
    private String email;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "회원 권한")
    private String authority;

    @ApiModelProperty(value = "학과")
    private String department;

    @ApiModelProperty(value = "프로필 사진")
    private String profileImgUrl;

    @ApiModelProperty(value = "자기소개글")
    private String introduction;

    public static MemberResponseDto create(MemberDto memberDto) {
        return new MemberResponseDto(memberDto.getId(), memberDto.getEmail(),
                memberDto.getAuthority(), memberDto.getNickname(), memberDto.getDepartment(),
                memberDto.getProfileImgUrl(), memberDto.getIntroduction());
    }
}
