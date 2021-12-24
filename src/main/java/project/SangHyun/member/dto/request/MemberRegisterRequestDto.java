package project.SangHyun.member.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.enums.MemberRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "회원가입 요청")
public class MemberRegisterRequestDto {

    @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요", required = true, example = "GilSSang")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String email;

    @ApiModelProperty(value = "비밀번호", notes = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.", required = true, example = "123456a!")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @ApiModelProperty(value = "닉네임", notes = "닉네임은 한글, 알파벳 숫자로 입력해주세요.", required = true, example = "GilSSang")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min=2, message = "닉네임이 너무 짧습니다.")
    @Pattern(regexp = "^[A-Za-z가-힣1-9]+$", message = "닉네임은 한글, 알파벳 숫자로만 입력해주세요.")
    private String nickname;

    @ApiModelProperty(value = "학과", notes = "학과 중 하나를 선택해주세요.", required = true, example = "컴퓨터공학과")
    private String department;

    @ApiModelProperty(value = "프로필 이미지", notes = "프로필 이미지를 업로드해주세요.", required = true, example = "")
    private MultipartFile profileImg;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return new Member(email, passwordEncoder.encode(password), nickname,
                department, MemberRole.ROLE_NOT_PERMITTED);
    }
}
