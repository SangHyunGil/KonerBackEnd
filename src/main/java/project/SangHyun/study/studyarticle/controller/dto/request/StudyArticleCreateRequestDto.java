package project.SangHyun.study.studyarticle.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleCreateDto;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시글 생성 요청")
public class StudyArticleCreateRequestDto {

    @ApiModelProperty(value = "스터디 게시글 생성자", notes = "스터디 게시글 생성자를 입력해주세요.", required = true, example = "1L")
    private Long memberId;

    @ApiModelProperty(value = "스터디 게시글 제목", notes = "스터디 게시글  제목을 입력해주세요.", required = true, example = "백엔드 스터디")
    @NotBlank(message = "스터디 게시글  제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용", notes = "스터디 게시글 내용을 입력해주세요.", required = true, example = "내용")
    @NotBlank(message = "스터디 게시글 내용을 입력해주세요.")
    private String content;

    public StudyArticleCreateDto toServiceDto() {
        return new StudyArticleCreateDto(memberId, title, content);
    }
}
