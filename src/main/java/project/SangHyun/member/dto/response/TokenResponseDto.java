package project.SangHyun.member.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.member.service.impl.JwtTokens;
import project.SangHyun.study.studyjoin.repository.impl.StudyInfoDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public static TokenResponseDto create(Member member, List<StudyInfoDto> studyInfos, JwtTokens jwtTokens) {
        return new TokenResponseDto(member.getId(), member.getEmail(), member.getNickname(), member.getDepartment(),
                studyInfos, jwtTokens.getAccessToken(), jwtTokens.getRefreshToken());
    }
}
