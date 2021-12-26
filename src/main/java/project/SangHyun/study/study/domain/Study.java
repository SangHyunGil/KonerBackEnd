package project.SangHyun.study.study.domain;

import lombok.*;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.study.enums.RecruitState;
import project.SangHyun.study.study.enums.StudyMethod;
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
    private String profileImgUrl;
    @Enumerated(EnumType.STRING)
    private StudyState studyState;
    @Enumerated(EnumType.STRING)
    private RecruitState recruitState;
    @Enumerated(EnumType.STRING)
    private StudyMethod studyMethod;
    private Long headCount;
    private String schedule;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "study", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<StudyJoin> studyJoins = new ArrayList<>();
    @OneToMany(mappedBy = "study", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<StudyBoard> studyBoards = new ArrayList<>();

    public Study(Long id) {
        this.id = id;
    }

    @Builder
    public Study(String title, String topic, String content, String profileImgUrl, StudyState studyState, RecruitState recruitState, Long headCount, String schedule, StudyMethod studyMethod, Member member, List<StudyJoin> studyJoins, List<StudyBoard> studyBoards) {
        this.title = title;
        this.topic = topic;
        this.content = content;
        this.profileImgUrl = profileImgUrl;
        this.studyState = studyState;
        this.recruitState = recruitState;
        this.headCount = headCount;
        this.schedule = schedule;
        this.studyMethod = studyMethod;
        this.member = member;
        this.studyJoins = studyJoins;
        this.studyBoards = studyBoards;
    }

    public Study updateStudyInfo(StudyUpdateRequestDto requestDto, String profileImgUrl) {
        this.title = requestDto.getTitle();
        this.topic = requestDto.getTopic();
        this.content = requestDto.getContent();
        this.schedule = requestDto.getSchedule();
        this.studyState = requestDto.getStudyState();
        this.recruitState = requestDto.getRecruitState();
        this.headCount = requestDto.getHeadCount();
        this.profileImgUrl = profileImgUrl;
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

    public void deleteStudyJoin(StudyJoin studyJoin) {
        this.studyJoins.remove(studyJoin);
    }
}
