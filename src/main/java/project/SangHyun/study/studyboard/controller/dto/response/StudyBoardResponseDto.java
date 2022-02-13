package project.SangHyun.study.studyboard.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyboard.service.dto.response.StudyBoardDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시판 반환 DTO")
public class StudyBoardResponseDto {

    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "스터디 게시판 제목")
    private String title;

    public static StudyBoardResponseDto create(StudyBoardDto studyBoardDto) {
        return new StudyBoardResponseDto(studyBoardDto.getId(), studyBoardDto.getTitle());
    }
}
