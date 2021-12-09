package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyJoin;

@Data
@NoArgsConstructor
public class StudyJoinResponseDto {
    private Long studyJoinId;
    private Long studyId;
    private Long memberId;

    public static StudyJoinResponseDto createDto(StudyJoin studyJoin) {
        return StudyJoinResponseDto.builder()
                .studyJoinId(studyJoin.getId())
                .studyId(studyJoin.getStudy().getId())
                .memberId(studyJoin.getMember().getId())
                .build();
    }

    @Builder
    public StudyJoinResponseDto(Long studyJoinId, Long studyId, Long memberId) {
        this.studyJoinId = studyJoinId;
        this.studyId = studyId;
        this.memberId = memberId;
    }
}
