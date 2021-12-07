package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.StudyArticle;

@Data
@NoArgsConstructor
public class StudyArticleFindResponseDto {
    private Long boardId;
    private Long categoryId;
    private String memberName;
    private String title;
    private String content;

    public static StudyArticleFindResponseDto createDto(StudyArticle studyBoard) {
        return StudyArticleFindResponseDto.builder()
                .boardId(studyBoard.getId())
                .categoryId(studyBoard.getStudyBoard().getId())
                .memberName(studyBoard.getMember().getNickname())
                .title(studyBoard.getTitle())
                .content(studyBoard.getContent())
                .build();
    }

    @Builder
    public StudyArticleFindResponseDto(Long boardId, Long categoryId, String memberName, String title, String content) {
        this.boardId = boardId;
        this.categoryId = categoryId;
        this.memberName = memberName;
        this.title = title;
        this.content = content;
    }
}
