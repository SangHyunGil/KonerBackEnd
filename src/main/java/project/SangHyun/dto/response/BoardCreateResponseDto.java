package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Board;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

@Data
@NoArgsConstructor
public class BoardCreateResponseDto {
    private Long boardId;
    private Long memberId;
    private String title;
    private String topic;
    private String content;
    private Long headCount;
    private StudyState studyState;
    private RecruitState recruitState;

    public static BoardCreateResponseDto createDto(Board board) {
        return BoardCreateResponseDto.builder()
                .boardId(board.getId())
                .memberId(board.getMember().getId())
                .title(board.getTitle())
                .topic(board.getTopic())
                .content(board.getContent())
                .headCount(board.getHeadCount())
                .studyState(board.getStudyState())
                .recruitState(board.getRecruitState())
                .build();
    }

    @Builder
    public BoardCreateResponseDto(Long boardId, Long memberId, String title, String topic, String content, Long headCount, StudyState studyState, RecruitState recruitState) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.topic = topic;
        this.content = content;
        this.headCount = headCount;
        this.studyState = studyState;
        this.recruitState = recruitState;
    }
}
