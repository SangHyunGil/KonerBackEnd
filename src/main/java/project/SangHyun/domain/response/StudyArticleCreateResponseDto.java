package project.SangHyun.domain.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyArticle;

@Data
@NoArgsConstructor
public class StudyArticleCreateResponseDto {
    private Long boardId;
    private Long memberId;
    private Long categoryId;
    private String title;
    private String content;

    public static StudyArticleCreateResponseDto createDto(StudyArticle studyBoard) {
        return StudyArticleCreateResponseDto.builder()
                .boardId(studyBoard.getId())
                .memberId(studyBoard.getMember().getId())
                .categoryId(studyBoard.getStudyBoard().getId())
                .title(studyBoard.getTitle())
                .content(studyBoard.getContent())
                .build();
    }

    @Builder
    public StudyArticleCreateResponseDto(Long boardId, Long memberId, Long categoryId, String title, String content) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
    }
}
