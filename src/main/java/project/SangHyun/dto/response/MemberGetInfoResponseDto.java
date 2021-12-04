package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
public class MemberGetInfoResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String department;

    public static MemberGetInfoResponseDto createDto(Member member) {
        return MemberGetInfoResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .build();
    }

    @Builder
    public MemberGetInfoResponseDto(Long id, String email, String nickname, String department) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
    }
}
