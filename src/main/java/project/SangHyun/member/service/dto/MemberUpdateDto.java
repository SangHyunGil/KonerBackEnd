package project.SangHyun.member.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.member.domain.Department;
import project.SangHyun.member.domain.Member;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "멤버 수정 요청 서비스 계층 DTO")
public class MemberUpdateDto {

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "학과")
    private Department department;

    @ApiModelProperty(value = "프로필 이미지")
    private MultipartFile profileImg;

    @ApiModelProperty(value = "자기소개글")
    private String introduction;

    public Member toEntity() throws IOException {
        return new Member(nickname, department, introduction);
    }
}
