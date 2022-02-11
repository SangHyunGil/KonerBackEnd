package project.SangHyun.member.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.domain.MemberRole;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "멤버 회원가입 요청 서비스 계층 DTO")
public class MemberRegisterDto {

    @ApiModelProperty(value = "아이디")
    private String email;

    @ApiModelProperty(value = "비밀번호")
    private String password;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "학과")
    private Department department;

    @ApiModelProperty(value = "프로필 이미지")
    private MultipartFile profileImg;

    public Member toEntity(String password, String profileImgUrl) throws IOException {
        return new Member(email, password, nickname, department, profileImgUrl, MemberRole.ROLE_NOT_PERMITTED, null);
    }
}
