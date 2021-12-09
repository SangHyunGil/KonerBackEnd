package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "재발행 요청 결과")
public class TokenResponseDto {
    @ApiModelProperty(value = "회원 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "아이디")
    private String email;

    @ApiModelProperty(value = "닉네임")
    private String nickname;

    @ApiModelProperty(value = "학과")
    private String department;

    @ApiModelProperty(value = "스터디참여정보")
    private List<StudyInfoDto> studyInfos;

    @ApiModelProperty(value = "AccessToken")
    private String accessToken;

    @ApiModelProperty(value = "RefreshToken")
    private String refreshToken;

    public static TokenResponseDto createDto(Member member, List<StudyInfoDto> studyInfoDtos, String accessToken, String refreshToken) {
        return TokenResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .department(member.getDepartment())
                .studyInfos(studyInfoDtos)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Builder
    public TokenResponseDto(Long id, String email, String nickname, String department, List<StudyInfoDto> studyInfos, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.department = department;
        this.studyInfos = studyInfos;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
