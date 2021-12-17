package project.SangHyun.study.studyboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    public StudyBoard(Long id) {
        this.id = id;
    }

    @Builder
    public StudyBoard(String title, Study study) {
        this.title = title;
        this.study = study;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void setStudy(Study study) {
        this.study = study;
    }
}
