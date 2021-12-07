package project.SangHyun.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.enums.RecruitState;
import project.SangHyun.domain.enums.StudyState;
import project.SangHyun.dto.request.StudyUpdateRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

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
    private List<StudyBoardCategory> studyBoardCategories = new ArrayList<>();

    public Study updateStudyInfo(StudyUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.topic = requestDto.getTopic();
        this.content = requestDto.getContent();
        this.studyState = requestDto.getStudyState();
        this.recruitState = requestDto.getRecruitState();
        this.headCount = requestDto.getHeadCount();

        return this;
    }

    public Study(Long id) {
        this.id = id;
    }

    public void join(StudyJoin studyJoin) {
        this.studyJoins.add(studyJoin);
        studyJoin.setStudy(this);
    }

    public void addBoard(StudyBoardCategory studyBoardCategory) {
        this.studyBoardCategories.add(studyBoardCategory);
        studyBoardCategory.setStudy(this);
    }

    @Builder
    public Study(String title, String topic, String content, StudyState studyState, RecruitState recruitState, Long headCount, Member member, List<StudyJoin> studyJoins, List<StudyBoardCategory> studyBoardCategories) {
        this.title = title;
        this.topic = topic;
        this.content = content;
        this.studyState = studyState;
        this.recruitState = recruitState;
        this.headCount = headCount;
        this.member = member;
        this.studyJoins = studyJoins;
        this.studyBoardCategories = studyBoardCategories;
    }
}
