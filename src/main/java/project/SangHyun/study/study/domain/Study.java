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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Study extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;
    private String title;
    @ElementCollection
    @CollectionTable(name = "STUDY_TAG", joinColumns = @JoinColumn(name = "study_id"))
    @Column(name = "TAG_NAME")
    private List<String> tags = new ArrayList<>();
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
    public Study(String title, List<String> tags, String content, String profileImgUrl, StudyState studyState, RecruitState recruitState, StudyMethod studyMethod, Long headCount, String schedule, Member member, List<StudyJoin> studyJoins, List<StudyBoard> studyBoards) {
        this.title = title;
        this.tags = tags;
        this.content = content;
        this.profileImgUrl = profileImgUrl;
        this.studyState = studyState;
        this.recruitState = recruitState;
        this.studyMethod = studyMethod;
        this.headCount = headCount;
        this.schedule = schedule;
        this.member = member;
        this.studyJoins = studyJoins;
        this.studyBoards = studyBoards;
    }

    public Study update(StudyUpdateRequestDto requestDto, String profileImgUrl) {
        this.title = requestDto.getTitle();
        this.tags = requestDto.getTags();
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
