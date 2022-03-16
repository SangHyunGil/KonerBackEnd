package project.SangHyun.study.studyarticle.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.dto.response.MemberProfile;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시글 반환 서비스 계층 DTO")
public class StudyArticleDto {

    @ApiModelProperty(value = "스터디 게시글 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "스터디 게시글 작성자")
    private MemberProfile creator;

    @ApiModelProperty(value = "스터디 게시글 제목")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용")
    private String content;

    @ApiModelProperty(value = "스터디 게시글 조회수")
    private Long views;

    public static StudyArticleDto create(StudyArticle studyArticle) {
        return new StudyArticleDto(studyArticle.getId(), MemberProfile.create(studyArticle),
                studyArticle.getTitle(), studyArticle.getContent(), studyArticle.getViews());
    }
}
