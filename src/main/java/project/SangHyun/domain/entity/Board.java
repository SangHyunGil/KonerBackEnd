package project.SangHyun.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.dto.request.BoardCreateRequestDto;
import project.SangHyun.dto.request.BoardUpdateRequestDto;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String topic;
    private String content;
    @Enumerated(EnumType.STRING)
    private StudyState studyState;
    @Enumerated(EnumType.STRING)
    private RecruitState recruitState;
    private Long headCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "board")
    private List<StudyJoin> studyJoins;


    public static Board createBoard(BoardCreateRequestDto requestDto, Member member) {
        return Board.builder()
                .title(requestDto.getTitle())
                .topic(requestDto.getTopic())
                .content(requestDto.getContent())
                .studyState(requestDto.getStudyState())
                .recruitState(requestDto.getRecruitState())
                .headCount(requestDto.getHeadCount())
                .member(member)
                .build();
    }

    public Board updateBoardInfo(BoardUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.topic = requestDto.getTopic();
        this.content = requestDto.getContent();
        this.studyState = requestDto.getStudyState();
        this.recruitState = requestDto.getRecruitState();
        this.headCount = requestDto.getHeadCount();

        return this;
    }

    @Builder
    public Board(String title, String topic, String content, StudyState studyState, RecruitState recruitState, Long headCount, Member member, List<StudyJoin> studyJoins) {
        this.title = title;
        this.topic = topic;
        this.content = content;
        this.studyState = studyState;
        this.recruitState = recruitState;
        this.headCount = headCount;
        this.member = member;
        this.studyJoins = studyJoins;
    }
}
