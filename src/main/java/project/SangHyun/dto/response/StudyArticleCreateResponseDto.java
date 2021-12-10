package project.SangHyun.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyArticle;

@Data
@NoArgsConstructor
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

    public static StudyArticleCreateResponseDto createDto(StudyArticle studyArticle) {
        return StudyArticleCreateResponseDto.builder()
                .articleId(studyArticle.getId())
                .memberId(studyArticle.getMember().getId())
                .boardId(studyArticle.getStudyBoard().getId())
                .title(studyArticle.getTitle())
                .content(studyArticle.getContent())
                .build();
    }

    @Builder
    public StudyArticleCreateResponseDto(Long articleId, Long memberId, Long boardId, String title, String content) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }
}
