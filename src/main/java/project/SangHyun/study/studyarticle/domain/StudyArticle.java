package project.SangHyun.study.studyarticle.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.SangHyun.common.EntityDate;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyboard.domain.StudyBoard;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class StudyArticle extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Embedded
    private StudyArticleTitle title;

    @Embedded
    private StudyArticleContent content;

    @Embedded
    private Views views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudyBoard studyBoard;

    public StudyArticle(Long id) {
        this.id = id;
    }

    public StudyArticle(String title, String content) {
        this.title = new StudyArticleTitle(title);
        this.content = new StudyArticleContent(content);
    }

    @Builder
    public StudyArticle(String title, String content, Long views, Member member, StudyBoard studyBoard) {
        this.title = new StudyArticleTitle(title);
        this.content = new StudyArticleContent(content);
        this.views = new Views(views);
        this.member = member;
        this.studyBoard = studyBoard;
    }

    public void updateArticleInfo(StudyArticle studyArticle) {
        this.title = new StudyArticleTitle(studyArticle.getTitle());
        this.content = new StudyArticleContent(studyArticle.getContent());
    }

    public void updateViews() {
        this.views.increase();
    }

    public String getCreatorNickname() {
        return member.getNickname();
    }

    public String getCreatorProfileUrlImg() {
        return member.getProfileImgUrl();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public Long getViews() {
        return views.getViews();
    }
}
