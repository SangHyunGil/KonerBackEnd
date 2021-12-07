package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

@Data
@NoArgsConstructor
public class StudyCreateResponseDto {
    private Long studyId;
    private Long memberId;
    private String title;
    private String topic;
    private String content;
    private Long headCount;
    private StudyState studyState;
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
