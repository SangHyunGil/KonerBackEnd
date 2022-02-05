package project.SangHyun.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.member.domain.Department;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원정보 수정 요청")
public class MemberUpdateRequestDto {
    @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요", required = true, example = "GilSSang")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String email;

    @ApiModelProperty(value = "닉네임", notes = "닉네임은 한글, 알파벳 숫자로 입력해주세요.", required = true, example = "GilSSang")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min=2, message = "닉네임이 너무 짧습니다.")
    @Pattern(regexp = "^[A-Za-z가-힣1-9]+$", message = "닉네임은 한글, 알파벳 숫자로만 입력해주세요.")
    private String nickname;

    @ApiModelProperty(value = "학과", notes = "학과 중 하나를 선택해주세요.", required = true, example = "컴퓨터공학과")
    private Department department;

    @ApiModelProperty(value = "프로필 이미지", notes = "프로필 이미지를 업로드해주세요.", required = true, example = "")
    private MultipartFile profileImg;
}
