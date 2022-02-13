package project.SangHyun.study.study.domain;

import lombok.*;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyOptions;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;
import project.SangHyun.study.study.domain.Tag.Tag;
import project.SangHyun.study.study.domain.Tag.Tags;
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

    @Embedded
    private StudyTitle title;

    @Embedded
    private Description description;

    @Embedded
    private HeadCount headCount;

    @Embedded
    private StudyProfileImgUrl profileImgUrl;

    @Embedded
    private StudyOptions studyOptions;

    @Embedded
    private Period period;

    @Embedded
    private Tags tags;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyCategory category;

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

    public Study(String title, List<String> tags, String description, String startDate, String endDate, StudyCategory category, Long headCount, StudyMethod studyMethod, StudyState studyState, RecruitState recruitState) {
        this.title = new StudyTitle(title);
        this.description = new Description(description);
        this.tags = new Tags(tags.stream().map(tag -> new Tag(tag)).collect(Collectors.toList()));
        this.headCount = new HeadCount(headCount);
        this.period = new Period(startDate, endDate);
        this.studyOptions = new StudyOptions(studyState, recruitState, studyMethod);
        this.category = category;
    }

    @Builder
    public Study(String title, List<String> tags, String description, String profileImgUrl, Long headCount, String startDate, String endDate, StudyCategory category, StudyMethod studyMethod, StudyState studyState, RecruitState recruitState, Member member, List<StudyJoin> studyJoins, List<StudyBoard> studyBoards) {
        this.title = new StudyTitle(title);
        this.description = new Description(description);
        this.tags = new Tags(tags.stream().map(tag -> new Tag(tag)).collect(Collectors.toList()));
        this.profileImgUrl = new StudyProfileImgUrl(profileImgUrl);
        this.headCount = new HeadCount(headCount);
        this.period = new Period(startDate, endDate);
        this.studyOptions = new StudyOptions(studyState, recruitState, studyMethod);
        this.category = category;
        this.member = member;
        this.studyJoins = studyJoins;
        this.studyBoards = studyBoards;
    }

    public Study update(Study study, String profileImgUrl) {
        this.title = new StudyTitle(study.getTitle());
        this.description = new Description(study.getDescription());
        this.tags = new Tags(study.getTagNames().stream().map(tag -> new Tag(tag)).collect(Collectors.toList()));
        this.period = new Period(study.getStartDate(), study.getEndDate());
        this.studyOptions = new StudyOptions(study.getStudyState(), study.getRecruitState(), study.getStudyMethod());
        this.headCount = new HeadCount(study.getHeadCount());
        this.category = study.getCategory();
        if (profileImgUrl != null) {
            this.profileImgUrl = new StudyProfileImgUrl(profileImgUrl);
        }
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

    public String getTitle() {
        return title.getTitle();
    }

    public String getDescription() {
        return description.getDescription();
    }

    public Long getHeadCount() {
        return headCount.getHeadCount();
    }

    public String getProfileImgUrl() {
        return profileImgUrl.getProfileImgUrl();
    }

    public String getCreatorNickname() {
        return member.getNickname();
    }

    public String getCreatorProfileImgUrl() {
        return member.getProfileImgUrl();
    }

    public List<String> getTagNames() {
        return tags.getTagNames();
    }

    public String getStartDate() {
        return period.getStartDate();
    }

    public String getEndDate() {
        return period.getEndDate();
    }

    public StudyMethod getStudyMethod() {
        return studyOptions.getStudyMethod();
    }

    public StudyState getStudyState() {
        return studyOptions.getStudyState();
    }

    public RecruitState getRecruitState() {
        return studyOptions.getRecruitState();
    }
}
