package project.SangHyun.study.studycomment.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyComment extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private StudyArticle studyArticle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private StudyComment parent;
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<StudyComment> children = new ArrayList<>();
    private String content;

    @Builder
    public StudyComment(Member member, StudyArticle studyArticle, StudyComment parent, String content) {
        this.member = member;
        this.studyArticle = studyArticle;
        this.parent = parent;
        this.content = content;
    }

    public void setParent(StudyComment studyComment) {
        this.parent = studyComment;
    }

    public void addChild(StudyComment studyComment) {
        this.children.add(studyComment);
        studyComment.setParent(this);
    }

    public void belongTo(StudyArticle studyArticle) {
        this.studyArticle = studyArticle;
    }
}
