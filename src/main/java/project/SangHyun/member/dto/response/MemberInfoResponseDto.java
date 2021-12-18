package project.SangHyun.member.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyjoin.repository.impl.StudyInfoDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public static MemberInfoResponseDto create(Member member, List<StudyInfoDto> studyInfo) {
        return new MemberInfoResponseDto(member.getId(), member.getEmail(), member.getNickname(), member.getDepartment(), studyInfo);
    }
}
