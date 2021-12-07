package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyUpdateRequestDto {
    private Long studyId;
    private String title;
    private String topic;
    private String content;
    private Long headCount;
    private StudyState studyState;
    private RecruitState recruitState;
}
