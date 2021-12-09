package project.SangHyun.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.Member;
import project.SangHyun.domain.entity.StudyJoin;
import project.SangHyun.domain.enums.StudyRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyJoinRequestDto {
    private Long studyId;
    private Long memberId;

    public StudyJoin toEntity() {
        return StudyJoin.builder()
                .study(new Study(studyId))
                .member(new Member(memberId))
                .studyRole(StudyRole.MEMBER)
                .build();
    }
}
