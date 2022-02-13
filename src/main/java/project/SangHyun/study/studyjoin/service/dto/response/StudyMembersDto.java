package project.SangHyun.study.studyjoin.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디원 조회 결과 서비스 계층 DTO")
public class StudyMembersDto {

    @ApiModelProperty(value = "스터디원 닉네임(PK)")
    private String nickname;

    @ApiModelProperty(value = "스터디원 프로필이미지")
    private String profileUrlImg;

    @ApiModelProperty(value = "스터디원 권한")
    private StudyRole studyRole;

    @ApiModelProperty(value = "스터디원 지원 내용")
    private String applyContent;

    public static StudyMembersDto create(StudyMembersInfoDto studyMembersInfoDto) {
        return new StudyMembersDto(studyMembersInfoDto.getNickname(), studyMembersInfoDto.getProfileImgUrl(),
                studyMembersInfoDto.getStudyRole(), studyMembersInfoDto.getApplyContent());
    }
}
