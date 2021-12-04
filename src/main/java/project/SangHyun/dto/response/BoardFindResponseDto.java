package project.SangHyun.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Board;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;

@Data
@NoArgsConstructor
public class BoardFindResponseDto {
    private Long boardId;
    private Long memberId;
    private String title;
    private String topic;
    private Long headCount;
    private StudyState studyState;
    private RecruitState recruitState;

    public static BoardFindResponseDto createDto(Board board) {
        return BoardFindResponseDto.builder()
                .boardId(board.getId())
                .memberId(board.getMember().getId())
                .title(board.getTitle())
                .topic(board.getTopic())
                .headCount(board.getHeadCount())
                .studyState(board.getStudyState())
                .recruitState(board.getRecruitState())
                .build();
    }

    @Builder
    public BoardFindResponseDto(Long boardId, Long memberId, String title, String topic, Long headCount, StudyState studyState, RecruitState recruitState) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.topic = topic;
        this.headCount = headCount;
        this.studyState = studyState;
        this.recruitState = recruitState;
    }
}
