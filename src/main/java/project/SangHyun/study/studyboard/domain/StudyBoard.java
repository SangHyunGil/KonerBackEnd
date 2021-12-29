package project.SangHyun.study.studyboard.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.SangHyun.common.EntityDate;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class StudyBoard extends EntityDate {
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

    public void update(String title) {
        this.title = title;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public void deleteInStudyCollections() {
        this.study.getStudyBoards().remove(this);
    }
}
