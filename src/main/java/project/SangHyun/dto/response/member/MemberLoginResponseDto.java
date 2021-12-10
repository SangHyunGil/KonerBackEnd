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
@ApiModel(value = "로그인 요청 결과")
public class MemberLoginResponseDto {
    @ApiModelProperty(value = "회원 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "아이디")
    private String email;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "학과")
    private String department;

    @ApiModelProperty(value = "스터디 참여정보")
    private List<StudyInfoDto> studyInfos;

    @ApiModelProperty(value = "AccessToken")
    private String accessToken;

    @ApiModelProperty(value = "RefreshToken")
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
