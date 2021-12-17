package project.SangHyun.study.study.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyState;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 찾기 요청 결과")
public class StudyFindResponseDto {
    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long studyId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private String topic;

    @ApiModelProperty(value = "스터디 내용")
    private String content;

    @ApiModelProperty(value = "스터디 참여수")
    private Long joinCount;

    @ApiModelProperty(value = "스터디 정원수")
    private Long headCount;

    @ApiModelProperty(value = "스터디 참여인원들")
    private List<String> studyMembers;

    @ApiModelProperty(value = "스터디 상태")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태")
    private RecruitState recruitState;

    public static StudyFindResponseDto create(Study study) {
        List<String> studyMembers = study.getStudyJoins().stream()
                .map(studyJoin -> studyJoin.getMember().getNickname())
                .collect(Collectors.toList());

        return new StudyFindResponseDto(study.getId(), study.getMember().getId(),
                study.getTitle(), study.getTopic(), study.getContent(),
                (long) studyMembers.size(), study.getHeadCount(), studyMembers,
                study.getStudyState(), study.getRecruitState());
    }
}
