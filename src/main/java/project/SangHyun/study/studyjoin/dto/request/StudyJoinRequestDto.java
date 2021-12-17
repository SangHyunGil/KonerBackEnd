package project.SangHyun.study.studyjoin.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.study.enums.StudyRole;

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
