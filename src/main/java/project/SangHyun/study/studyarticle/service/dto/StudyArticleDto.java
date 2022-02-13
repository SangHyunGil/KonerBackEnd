package project.SangHyun.study.studyarticle.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyArticleDto {

    @ApiModelProperty(value = "스터디 게시글 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "스터디 게시글 작성자 이름")
    private String nickname;

    @ApiModelProperty(value = "스터디 게시글 제목")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용")
    private String content;

    @ApiModelProperty(value = "스터디 게시글 조회수")
    private Long views;

    public static StudyArticleDto create(StudyArticle studyArticle) {
        return new StudyArticleDto(studyArticle.getId(), studyArticle.getCreatorNickname(),
                studyArticle.getTitle(), studyArticle.getContent(), studyArticle.getViews());
    }
}
