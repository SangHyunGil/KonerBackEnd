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
public class MemberInfoResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String department;
    private List<StudyInfoDto> studyInfos;

    public static MemberInfoResponseDto createDto(Member member, List<StudyInfoDto> studyInfoDtos) {
        return MemberInfoResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .studyInfos(studyInfoDtos)
                .build();
    }

    @Builder
    public MemberInfoResponseDto(Long memberId, String email, String nickname, String department, List<StudyInfoDto> studyInfos) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
        this.studyInfos = studyInfos;
    }
}
