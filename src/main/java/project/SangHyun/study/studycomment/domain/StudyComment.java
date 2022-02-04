package project.SangHyun.study.studycomment.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class StudyComment extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudyArticle studyArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudyComment parent;

    @OneToMany(mappedBy = "parent")
    private List<StudyComment> children = new ArrayList<>();

    public StudyComment(Long id) {
        this.id = id;
    }

    @Builder
    public StudyComment(Member member, StudyArticle studyArticle, StudyComment parent, String content, Boolean isDeleted) {
        this.member = member;
        this.studyArticle = studyArticle;
        this.parent = parent;
        this.content = content;
        this.deleted = isDeleted;
    }

    public void setParent(StudyComment studyComment) {
        this.parent = studyComment;
    }

    public void addChild(StudyComment studyComment) {
        this.children.add(studyComment);
        studyComment.setParent(this);
    }

    public void delete() {
        this.deleted = true;
    }

    public void update(String content) {
        this.content = content;
    }

    public Optional<StudyComment> findDeletableComment() {
        return hasChildren() ? Optional.empty() : Optional.ofNullable(findDeletableRootComment());
    }

    private Boolean hasChildren() {
        return getChildren().size() != 0;
    }

    private StudyComment findDeletableRootComment() {
        if (isDeletableParent()) {
            StudyComment deletableParentComment = getParent().findDeletableRootComment();
            if (isParentHasOnlyDeletedChildren())
                return deletableParentComment;
        }
        return this;
    }

    private Boolean isDeletableParent() {
        return getParent() != null && getParent().isDeleted();
    }

    public Boolean isDeleted() {
        return deleted;
    }

    private Boolean isParentHasOnlyDeletedChildren() {
        return getParent().getChildren().size() == 1;
    }
}
