package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyBoard;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 게시판 찾기 요청 결과")
public class StudyBoardFindResponseDto {
    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    Long studyBoardId;

    @ApiModelProperty(value = "스터디 게시판 제목")
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
