package project.SangHyun.study.studyjoin.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyRole;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 참가 생성 요청 서비스 계층 DTO")
public class StudyJoinCreateDto {

    @ApiModelProperty(value = "스터디 지원 내용")
    private String applyContent;

    public StudyJoin toEntity(Long studyId, Long memberId) {
        return StudyJoin.builder()
                .study(new Study(studyId))
                .member(new Member(memberId))
                .studyRole(StudyRole.APPLY)
                .applyContent(applyContent)
                .build();
    }
}
