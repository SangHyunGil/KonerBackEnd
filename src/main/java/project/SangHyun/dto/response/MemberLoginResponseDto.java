package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.repository.impl.dto.StudyInfoDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class MemberLoginResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String department;
    private List<StudyInfoDto> studyInfos;
    private String accessToken;
    private String refreshToken;

    public static MemberLoginResponseDto createDto(Member member, List<StudyInfoDto> studyInfos, String accessToken, String refreshToken) {
        return MemberLoginResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .studyInfos(studyInfos)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Builder
    public MemberLoginResponseDto(Long id, String email, String nickname, String department, List<StudyInfoDto> studyInfos, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
        this.studyInfos = studyInfos;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
