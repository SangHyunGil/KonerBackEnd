package project.SangHyun.dto.response.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 생성 요청 결과")
public class StudyCreateResponseDto {
    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long studyId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "스터디 제목")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private String topic;

    @ApiModelProperty(value = "스터디 내용")
    private String content;

    @ApiModelProperty(value = "스터디 정원")
    private Long headCount;

    @ApiModelProperty(value = "스터디 상태")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태")
    private RecruitState recruitState;

    public static StudyCreateResponseDto createDto(Study study) {
        return StudyCreateResponseDto.builder()
                .studyId(study.getId())
                .memberId(study.getMember().getId())
                .title(study.getTitle())
                .topic(study.getTopic())
                .content(study.getContent())
                .headCount(study.getHeadCount())
                .studyState(study.getStudyState())
                .recruitState(study.getRecruitState())
                .build();
    }

    @Builder
    public StudyCreateResponseDto(Long studyId, Long memberId, String title, String topic, String content, Long headCount, StudyState studyState, RecruitState recruitState) {
        this.studyId = studyId;
        this.memberId = memberId;
        this.title = title;
        this.topic = topic;
        this.content = content;
        this.headCount = headCount;
        this.studyState = studyState;
        this.recruitState = recruitState;
    }
}
