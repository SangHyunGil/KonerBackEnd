package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyArticle;

@Data
@NoArgsConstructor
public class StudyArticleFindResponseDto {
    private Long articleId;
    private Long boardId;
    private String memberName;
    private String title;
    private String content;

    public static StudyArticleFindResponseDto createDto(StudyArticle studyArticle) {
        return StudyArticleFindResponseDto.builder()
                .articleId(studyArticle.getId())
                .boardId(studyArticle.getStudyBoard().getId())
                .memberName(studyArticle.getMember().getNickname())
                .title(studyArticle.getTitle())
                .content(studyArticle.getContent())
                .build();
    }

    @Builder
    public StudyArticleFindResponseDto(Long articleId, Long boardId, String memberName, String title, String content) {
        this.articleId = articleId;
        this.boardId = boardId;
        this.memberName = memberName;
        this.title = title;
        this.content = content;
    }
}
