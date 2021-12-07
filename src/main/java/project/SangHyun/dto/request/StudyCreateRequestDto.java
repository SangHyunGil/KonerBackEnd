package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyCreateRequestDto {
    private Long memberId;
    private String title;
    private String topic;
    private String content;
    private Long headCount;
    private StudyState studyState;
    private RecruitState recruitState;

    public Study toEntity() {
        return Study.builder()
                .title(this.title)
                .topic(this.topic)
                .content(this.content)
                .studyState(this.studyState)
                .member(new Member(this.memberId))
                .studyJoins(new ArrayList<>())
                .studyBoardCategories(new ArrayList<>())
                .recruitState(this.recruitState)
                .headCount(this.headCount)
                .build();
    }
}
