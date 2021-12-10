package project.SangHyun.dto.response.member;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.repository.impl.dto.StudyInfoDto;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(value = "회원 정보 요청 결과")
public class MemberInfoResponseDto {
    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "아이디")
    private String email;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "학과")
    private String department;

    @ApiModelProperty(value = "스터디 정보")
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
