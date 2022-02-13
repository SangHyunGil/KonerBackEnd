package project.SangHyun.study.studyboard.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyboard.service.dto.request.StudyBoardCreateDto;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시판 생성 요청")
public class StudyBoardCreateRequestDto {

    @ApiModelProperty(value = "게시판 제목", notes = "게시판 제목을 입력해주세요.", required = true, example = "알고리즘 게시판")
    @NotBlank(message = "게시판 제목을 입력해주세요.")
    String title;

    public StudyBoardCreateDto toServiceDto() {
        return new StudyBoardCreateDto(title);
    }
}
