package project.SangHyun.study.studyarticle.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.SangHyun.common.EntityDate;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;
import project.SangHyun.study.studycomment.domain.StudyComment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class StudyArticle extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;
    private String title;
    private String content;
    private Long views;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudyBoard studyBoard;

    @Builder
    public StudyArticle(String title, String content, Long views, Member member, StudyBoard studyBoard) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.member = member;
        this.studyBoard = studyBoard;
    }

    public StudyArticle(Long id) {
        this.id = id;
    }

    public void updateArticleInfo(StudyArticleUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void updateViews() {
        this.views += 1;
    }

    public void belongTo(StudyBoard studyBoard) {
        this.studyBoard = studyBoard;
    }
}
