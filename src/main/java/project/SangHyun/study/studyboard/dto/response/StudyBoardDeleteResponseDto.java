package project.SangHyun.study.studyboard.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyboard.domain.StudyBoard;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시판 삭제 요청 결과")
public class StudyBoardDeleteResponseDto {
    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    private Long studyBoardId;

    @ApiModelProperty(value = "스터디 게시판 제목")
    private String title;

    public static StudyBoardDeleteResponseDto create(StudyBoard studyBoard) {
        return new StudyBoardDeleteResponseDto(studyBoard.getId(), studyBoard.getTitle());
    }
}
