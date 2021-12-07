package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyBoard;

@Data
@NoArgsConstructor
public class StudyBoardCreateResponseDto {
    private Long studyBoardId;
    private Long studyId;
    private String title;

    public static StudyBoardCreateResponseDto createDto(StudyBoard studyBoard) {
        return StudyBoardCreateResponseDto.builder()
                .studyBoardId(studyBoard.getId())
                .studyId(studyBoard.getStudy().getId())
                .title(studyBoard.getTitle())
                .build();
    }

    @Builder
    public StudyBoardCreateResponseDto(Long studyBoardId, Long studyId, String title) {
        this.studyBoardId = studyBoardId;
        this.studyId = studyId;
        this.title = title;
    }
}
