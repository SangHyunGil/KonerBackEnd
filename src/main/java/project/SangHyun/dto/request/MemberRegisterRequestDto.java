package project.SangHyun.dto.request;


import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.MemberRole;

@Data
@NoArgsConstructor
public class MemberRegisterRequestDto {
    private String email;
    private String password;
    private String nickname;
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
