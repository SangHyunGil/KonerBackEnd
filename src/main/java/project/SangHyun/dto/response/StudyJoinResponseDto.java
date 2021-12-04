package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyJoin;

@Data
@NoArgsConstructor
public class StudyJoinResponseDto {
    private Long studyJoinId;
    private Long boardId;
    private Long memberId;

    public static StudyJoinResponseDto createDto(StudyJoin studyJoin) {
        return StudyJoinResponseDto.builder()
                .studyJoinId(studyJoin.getId())
                .boardId(studyJoin.getBoard().getId())
                .memberId(studyJoin.getMember().getId())
                .build();
    }

    @Builder
    public StudyJoinResponseDto(Long studyJoinId, Long boardId, Long memberId) {
        this.studyJoinId = studyJoinId;
        this.boardId = boardId;
        this.memberId = memberId;
    }
}
