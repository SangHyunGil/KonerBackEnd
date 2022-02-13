package project.SangHyun.study.studyarticle.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyArticleUpdateDto {

    @ApiModelProperty(value = "스터디 게시글 제목", notes = "스터디 게시글  제목을 입력해주세요.", required = true, example = "백엔드 스터디")
    @NotBlank(message = "스터디 게시글  제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용", notes = "스터디 게시글 내용을 입력해주세요.", required = true, example = "내용")
    @NotBlank(message = "스터디 게시글 내용을 입력해주세요.")
    private String content;

    public StudyArticle toEntity() {
        return new StudyArticle(title, content);
    }
}
