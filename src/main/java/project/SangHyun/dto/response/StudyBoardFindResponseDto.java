package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyBoard;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class StudyBoardFindResponseDto {
    Long studyBoardId;
    String title;

    public static StudyBoardFindResponseDto createDto(StudyBoard studyBoard) {
        return StudyBoardFindResponseDto.builder()
                .studyBoardId(studyBoard.getId())
                .title(studyBoard.getTitle())
                .build();
    }

    @Builder

    public StudyBoardFindResponseDto(Long studyBoardId, String title) {
        this.studyBoardId = studyBoardId;
        this.title = title;
    }
}
