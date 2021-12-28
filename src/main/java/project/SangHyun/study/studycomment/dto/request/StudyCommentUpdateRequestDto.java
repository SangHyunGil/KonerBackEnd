package project.SangHyun.study.studycomment.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 댓글 수정 요청")
public class StudyCommentUpdateRequestDto {
    @ApiModelProperty(value = "스터디 댓글 내용", notes = "스터디 댓글 내용 입력해주세요.", required = true, example = "테스트")
    private String content;
}
