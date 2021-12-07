package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyJoin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyJoinRequestDto {
    private Long studyId;
    private Long memberId;

    public StudyJoin toEntity() {
        return StudyJoin.builder()
                .study(new Study(this.studyId))
                .member(new Member(this.memberId))
                .build();
    }
}
