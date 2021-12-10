package project.SangHyun.dto.response.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyArticle;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 게시글 업데이트 요청 결과")
public class StudyArticleUpdateResponseDto {
    @ApiModelProperty(value = "스터디 게시글 ID(PK)")
    private Long articleId;

    @ApiModelProperty(value = "스터디 게시판 ID(PK)")
    private Long boardId;

    @ApiModelProperty(value = "스터디 게시글 작성자 이름")
    private String memberName;

    @ApiModelProperty(value = "스터디 게시글 제목")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용")
    private String content;

    public static StudyArticleUpdateResponseDto createDto(StudyArticle studyArticle) {
        return StudyArticleUpdateResponseDto.builder()
                .articleId(studyArticle.getId())
                .boardId(studyArticle.getStudyBoard().getId())
                .memberName(studyArticle.getMember().getNickname())
                .title(studyArticle.getTitle())
                .content(studyArticle.getContent())
                .build();
    }

    @Builder
    public StudyArticleUpdateResponseDto(Long articleId, Long boardId, String memberName, String title, String content) {
        this.articleId = articleId;
        this.boardId = boardId;
        this.memberName = memberName;
        this.title = title;
        this.content = content;
    }
}
