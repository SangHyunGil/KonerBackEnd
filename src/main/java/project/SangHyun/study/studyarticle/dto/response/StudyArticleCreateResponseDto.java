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
@ApiModel(value = "스터디 게시글 생성 요청 결과")
public class StudyArticleCreateResponseDto {
    @ApiModelProperty(value = "스터디 게시글 ID(PK)")
    private Long articleId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    private Long boardId;

    @ApiModelProperty(value = "스터디 게시글 제목")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용")
    private String content;

    public static StudyArticleCreateResponseDto create(StudyArticle studyArticle) {
        return new StudyArticleCreateResponseDto(studyArticle.getId(), studyArticle.getMember().getId(),
                studyArticle.getStudyBoard().getId(), studyArticle.getTitle(), studyArticle.getContent());
    }
}
