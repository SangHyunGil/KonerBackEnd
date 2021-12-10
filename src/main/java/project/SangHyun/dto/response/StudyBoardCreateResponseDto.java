package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyBoard;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 게시판 생성 요청 결과")
public class StudyBoardCreateResponseDto {
    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    private Long studyBoardId;

    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long studyId;

    @ApiModelProperty(value = "스터디 게시판 제목")
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
