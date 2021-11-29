package project.SangHyun.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
public class TokenResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String department;
    private String accessToken;
    private String refreshToken;

    public static TokenResponseDto createDto(Member member, String accessToken, String refreshToken) {
        return TokenResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Builder
    public TokenResponseDto(Long id, String email, String nickname, String department, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
