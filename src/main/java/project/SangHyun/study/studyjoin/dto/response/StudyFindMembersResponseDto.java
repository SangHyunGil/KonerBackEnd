package project.SangHyun.study.studyjoin.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.enums.StudyRole;
import project.SangHyun.study.studyjoin.repository.impl.StudyMembersInfoDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디원 조회 결과")
public class StudyFindMembersResponseDto {
    @ApiModelProperty(value = "스터디원 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "스터디원 닉네임(PK)")
    private String name;

    @ApiModelProperty(value = "스터디원 권한(PK)")
    private StudyRole studyRole;

    public static StudyFindMembersResponseDto create(StudyMembersInfoDto studyMembersInfoDto) {
        return new StudyFindMembersResponseDto(studyMembersInfoDto.getMemberId(), studyMembersInfoDto.getMemberName(), studyMembersInfoDto.getStudyRole());
    }
}
