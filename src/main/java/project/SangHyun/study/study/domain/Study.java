package project.SangHyun.study.study.domain;

import lombok.*;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.Tag.Tag;
import project.SangHyun.study.study.domain.Tag.Tags;
import project.SangHyun.study.study.dto.request.StudyUpdateRequestDto;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.study.studyjoin.domain.StudyJoin;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Study extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private Long headCount;

    @Column(nullable = false)
    private String profileImgUrl;

    @Embedded
    @Column(nullable = false)
    private StudyOptions studyOptions;

    @Embedded
    @Column(nullable = false)
    private Schedule schedule;

    @Embedded
    @Column(nullable = false)
    private Tags tags = new Tags();

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
    public Study(String title, Tags tags, String content, String profileImgUrl, String department, StudyOptions studyOptions, Long headCount, Schedule schedule, Member member, List<StudyJoin> studyJoins, List<StudyBoard> studyBoards) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.profileImgUrl = profileImgUrl;
        this.department = department;
        this.studyOptions = studyOptions;
        this.headCount = headCount;
        this.schedule = schedule;
        this.member = member;
        this.studyJoins = studyJoins;
        this.studyBoards = studyBoards;
    }

    public Study update(StudyUpdateRequestDto requestDto, String profileImgUrl) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.tags = new Tags(requestDto.getTags().stream().map(tag -> new Tag(tag)).collect(Collectors.toList()));
        this.schedule = new Schedule(requestDto.getStartDate(), requestDto.getEndDate());
        this.studyOptions = new StudyOptions(requestDto.getStudyState(), requestDto.getRecruitState(), requestDto.getStudyMethod());
        this.headCount = requestDto.getHeadCount();
        this.department = requestDto.getDepartment();
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
}
