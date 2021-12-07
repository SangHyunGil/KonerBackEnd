package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyBoard;

@Data
@NoArgsConstructor
public class StudyBoardUpdateResponseDto {
    private Long studyBoardId;
    private Long studyId;
    private String title;

    public static StudyBoardUpdateResponseDto createDto(StudyBoard studyBoard) {
        return StudyBoardUpdateResponseDto.builder()
                .studyBoardId(studyBoard.getId())
                .studyId(studyBoard.getStudy().getId())
                .title(studyBoard.getTitle())
                .build();
    }

    @Builder
    public StudyBoardUpdateResponseDto(Long studyBoardId, Long studyId, String title) {
        this.studyBoardId = studyBoardId;
        this.studyId = studyId;
        this.title = title;
    }
}
