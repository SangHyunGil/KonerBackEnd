package project.SangHyun.dto.request.member;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
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

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .department(department)
                .memberRole(MemberRole.ROLE_NOT_PERMITTED)
                .build();
    }

    @Builder
    public MemberRegisterRequestDto(String email, String password, String nickname, String department) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.department = department;
    }
}
