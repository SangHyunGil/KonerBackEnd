package project.SangHyun.study.studyarticle.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시글 삭제 요청 결과")
public class StudyArticleDeleteResponseDto {
    @ApiModelProperty(value = "스터디 게시글 ID(PK)")
    private Long articleId;

    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    private Long boardId;

    @ApiModelProperty(value = "스터디 게시글 작성자 이름")
    private String memberName;

    @ApiModelProperty(value = "스터디 게시글 제목")
    private String title;

    public static StudyArticleDeleteResponseDto create(StudyArticle studyArticle) {
        return new StudyArticleDeleteResponseDto(studyArticle.getId(), studyArticle.getStudyBoard().getId(),
                studyArticle.getCreatorNickname(), studyArticle.getTitle());
    }
}
