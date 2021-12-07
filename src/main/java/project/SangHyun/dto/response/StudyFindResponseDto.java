package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

@Data
@NoArgsConstructor
public class StudyFindResponseDto {
    private Long studyId;
    private Long memberId;
    private String title;
    private String topic;
    private Long joinCount;
    private Long headCount;
    private StudyState studyState;
    private RecruitState recruitState;

    public static StudyFindResponseDto createDto(Study study) {
        return StudyFindResponseDto.builder()
                .studyId(study.getId())
                .memberId(study.getMember().getId())
                .title(study.getTitle())
                .topic(study.getTopic())
                .joinCount(study.getStudyJoins().stream().count())
                .headCount(study.getHeadCount())
                .studyState(study.getStudyState())
                .recruitState(study.getRecruitState())
                .build();
    }

    @Builder
    public StudyFindResponseDto(Long studyId, Long memberId, String title, String topic, Long joinCount, Long headCount, StudyState studyState, RecruitState recruitState) {
        this.studyId = studyId;
        this.memberId = memberId;
        this.title = title;
        this.topic = topic;
        this.joinCount = joinCount;
        this.headCount = headCount;
        this.studyState = studyState;
        this.recruitState = recruitState;
    }
}
