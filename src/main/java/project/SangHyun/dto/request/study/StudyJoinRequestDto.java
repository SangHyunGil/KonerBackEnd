package project.SangHyun.dto.request.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 참여 요청")
public class StudyJoinRequestDto {
    @ApiModelProperty(value = "스터디 ID(PK)", notes = "스터디 ID(PK)를 입력해주세요.", required = true, example = "1L")
    private Long studyId;

    @ApiModelProperty(value = "회원 ID(PK)", notes = "회원 ID(PK)를 입력해주세요.", required = true, example = "1L")
    private Long memberId;

    public StudyJoin toEntity() {
        return StudyJoin.builder()
                .study(new Study(studyId))
                .member(new Member(memberId))
                .studyRole(StudyRole.MEMBER)
                .build();
    }
}
