package project.SangHyun.study.study.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyMethod;
import project.SangHyun.study.study.enums.StudyState;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 업데이트 요청 결과")
public class StudyUpdateResponseDto {
    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long studyId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "스터디 제목")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private List<String> topic;

    @ApiModelProperty(value = "스터디 내용")
    private String content;

    @ApiModelProperty(value = "스터디 내용")
    private String schedule;

    @ApiModelProperty(value = "스터디 정원")
    private Long headCount;

    @ApiModelProperty(value = "스터디 방식", notes = "스터디 방식을 입력해주세요.", required = true, example = "대면")
    private StudyMethod studyMethod;

    @ApiModelProperty(value = "스터디 상태")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태")
    private RecruitState recruitState;

    public static StudyUpdateResponseDto create(Study study) {
        return new StudyUpdateResponseDto(study.getId(), study.getMember().getId(),
                study.getTitle(), study.getTags(), study.getContent(), study.getSchedule(),
                study.getHeadCount(), study.getStudyMethod(), study.getStudyState(), study.getRecruitState());
    }
}
