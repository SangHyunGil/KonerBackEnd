package project.SangHyun.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.dto.request.BoardCreateRequestDto;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private String topic;
    @Enumerated(EnumType.STRING)
    private StudyState studyState;
    @Enumerated(EnumType.STRING)
    private RecruitState recruitState;
    private Long headCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Board createBoard(BoardCreateRequestDto requestDto, Member member) {
        return Board.builder()
                .title(requestDto.getTitle())
                .topic(requestDto.getTopic())
                .studyState(requestDto.getStudyState())
                .recruitState(requestDto.getRecruitState())
                .headCount(requestDto.getHeadCount())
                .member(member)
                .build();
    }

    @Builder
    public Board(String title, String topic, StudyState studyState, RecruitState recruitState, Long headCount, Member member) {
        this.title = title;
        this.topic = topic;
        this.studyState = studyState;
        this.recruitState = recruitState;
        this.headCount = headCount;
        this.member = member;
    }
}
