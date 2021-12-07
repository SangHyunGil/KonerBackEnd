package project.SangHyun.domain.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;
import project.SangHyun.domain.entity.StudyBoard;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyBoardCreateRequestDto {
    String title;

    public StudyBoard toEntity(Long studyId) {
        return StudyBoard.builder()
                .title(title)
                .study(new Study(studyId))
                .build();
    }
}
