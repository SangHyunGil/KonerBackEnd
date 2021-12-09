package project.SangHyun.domain.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyArticle;

@Data
@NoArgsConstructor
public class StudyArticleCreateResponseDto {
    private Long articleId;
    private Long memberId;
    private Long boardId;
    private String title;
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
