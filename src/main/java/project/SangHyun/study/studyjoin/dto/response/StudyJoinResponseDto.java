package project.SangHyun.study.studyjoin.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyjoin.domain.StudyJoin;
import project.SangHyun.study.studyjoin.repository.impl.StudyInfoDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 참여 요청 결과")
public class StudyJoinResponseDto {
    @ApiModelProperty(value = "스터디 참여 ID(PK)")
    private Long studyJoinId;

    @ApiModelProperty(value = "스터디 참여 정보")
    private StudyInfoDto studyInfos;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    public static StudyJoinResponseDto create(StudyJoin studyJoin) {
        StudyInfoDto studyInfos = new StudyInfoDto(studyJoin.getStudy().getId(), studyJoin.getStudyRole());
        return new StudyJoinResponseDto(studyJoin.getId(), studyInfos, studyJoin.getMember().getId());
    }
}
