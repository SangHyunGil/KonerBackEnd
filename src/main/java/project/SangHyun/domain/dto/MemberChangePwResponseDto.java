package project.SangHyun.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;

@Data
@NoArgsConstructor
public class MemberChangePwResponseDto {
    Long id;
    String email;
    String password;

    public static MemberChangePwResponseDto createDto(Member member) {
        return MemberChangePwResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }

    @Builder
    public MemberChangePwResponseDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
