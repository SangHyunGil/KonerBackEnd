package project.SangHyun.study.studyjoin.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.service.dto.response.StudyMembersDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디원 조회 결과")
public class StudyMembersResponseDto {

    @ApiModelProperty(value = "스터디원 닉네임(PK)")
    private String nickname;

    @ApiModelProperty(value = "스터디원 프로필이미지")
    private String profileUrlImg;

    @ApiModelProperty(value = "스터디원 권한")
    private StudyRole studyRole;

    @ApiModelProperty(value = "스터디원 지원 내용")
    private String applyContent;

    public static StudyMembersResponseDto create(StudyMembersDto studyMembersDto) {
        return new StudyMembersResponseDto(studyMembersDto.getNickname(), studyMembersDto.getProfileUrlImg(),
                studyMembersDto.getStudyRole(), studyMembersDto.getApplyContent());
    }
}
