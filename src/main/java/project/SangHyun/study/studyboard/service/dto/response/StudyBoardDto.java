package project.SangHyun.study.studyboard.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyboard.domain.StudyBoard;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시판 반환 서비스 계층 DTO")
public class StudyBoardDto {

    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "스터디 게시판 제목")
    private String title;

    public static StudyBoardDto create(StudyBoard studyBoard) {
        return new StudyBoardDto(studyBoard.getId(), studyBoard.getTitle());
    }
}
