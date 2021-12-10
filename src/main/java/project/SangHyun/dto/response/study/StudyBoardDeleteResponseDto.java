package project.SangHyun.dto.response.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyBoard;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 게시판 삭제 요청 결과")
public class StudyBoardDeleteResponseDto {
    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    private Long studyBoardId;

    @ApiModelProperty(value = "스터디 게시판 제목")
    private String title;

    public static StudyBoardDeleteResponseDto createDto(StudyBoard studyBoard) {
        return StudyBoardDeleteResponseDto.builder()
                .studyBoardId(studyBoard.getId())
                .title(studyBoard.getTitle())
                .build();
    }

    @Builder
    public StudyBoardDeleteResponseDto(Long studyBoardId, String title) {
        this.studyBoardId = studyBoardId;
        this.title = title;
    }
}
