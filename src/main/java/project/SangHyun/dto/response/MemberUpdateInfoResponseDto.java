package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
public class MemberUpdateInfoResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String department;

    public static MemberUpdateInfoResponseDto createDto(Member member) {
        return MemberUpdateInfoResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .build();
    }

    @Builder
    public MemberUpdateInfoResponseDto(Long memberId, String email, String nickname, String department) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
    }
}
