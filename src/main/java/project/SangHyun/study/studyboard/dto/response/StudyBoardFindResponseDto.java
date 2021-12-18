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
@ApiModel(value = "스터디 게시판 찾기 요청 결과")
public class StudyBoardFindResponseDto {
    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    Long studyBoardId;

    @ApiModelProperty(value = "스터디 게시판 제목")
    String title;

    public static StudyBoardFindResponseDto create(StudyBoard studyBoard) {
        return new StudyBoardFindResponseDto(studyBoard.getId(), studyBoard.getTitle());
    }
}
