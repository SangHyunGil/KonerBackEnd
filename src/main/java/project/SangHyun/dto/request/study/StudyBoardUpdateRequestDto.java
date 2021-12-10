package project.SangHyun.dto.request.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시판 업데이트 요청")
public class StudyBoardUpdateRequestDto {
    @ApiModelProperty(value = "게시판 제목", notes = "게시판 제목을 입력해주세요.", required = true, example = "알고리즘 게시판")
    @NotBlank(message = "게시판 제목을 입력해주세요.")
    private String title;
}
