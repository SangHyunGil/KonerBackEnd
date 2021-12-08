package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MemberLoginResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String department;
    private List<Long> studyIds;
    private String accessToken;
    private String refreshToken;

    public static MemberLoginResponseDto createDto(Member member, List<Study> studies, String accessToken, String refreshToken) {
        List<Long> studyIds = studies.stream()
                .map(study -> study.getId())
                .collect(Collectors.toList());
        return MemberLoginResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .studyIds(studyIds)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Builder
    public MemberLoginResponseDto(Long id, String email, String nickname, String department, List<Long> studyIds, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
        this.studyIds = studyIds;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
