package project.SangHyun.study.studyboard.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시판 생성 요청 서비스 계층 DTO")
public class StudyBoardUpdateDto {

    @ApiModelProperty(value = "게시판 제목")
    String title;
}
