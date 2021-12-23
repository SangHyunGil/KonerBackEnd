package project.SangHyun.study.study.domain;

import lombok.*;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyState;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Study extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
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
    @OneToMany(mappedBy = "study", cascade = CascadeType.PERSIST)
    private List<StudyJoin> studyJoins = new ArrayList<>();
    @OneToMany(mappedBy = "study", cascade = CascadeType.PERSIST)
    private List<StudyBoard> studyBoards = new ArrayList<>();

    public Study(Long id) {
        this.id = id;
    }

    @Builder
    public Study(String title, String topic, String content, StudyState studyState, RecruitState recruitState, Long headCount, Member member, List<StudyJoin> studyJoins, List<StudyBoard> studyBoards) {
        this.title = title;
        this.topic = topic;
        this.content = content;
        this.studyState = studyState;
        this.recruitState = recruitState;
        this.headCount = headCount;
        this.member = member;
        this.studyJoins = studyJoins;
        this.studyBoards = studyBoards;
    }

    public Study updateStudyInfo(StudyUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.topic = requestDto.getTopic();
        this.content = requestDto.getContent();
        this.studyState = requestDto.getStudyState();
        this.recruitState = requestDto.getRecruitState();
        this.headCount = requestDto.getHeadCount();

        return this;
    }

    public void join(StudyJoin studyJoin) {
        this.studyJoins.add(studyJoin);
        studyJoin.setStudy(this);
    }

    public void addBoard(StudyBoard studyBoard) {
        this.studyBoards.add(studyBoard);
        studyBoard.setStudy(this);
    }
}
