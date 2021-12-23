package project.SangHyun.study.studyarticle.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.SangHyun.common.EntityDate;
import project.SangHyun.study.studyboard.domain.StudyBoard;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.dto.request.StudyArticleUpdateRequestDto;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private StudyBoard studyBoard;

    @Builder
    public StudyArticle(String title, String content, Long views, Member member, StudyBoard studyBoard) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.member = member;
        this.studyBoard = studyBoard;
    }

    public void updateArticleInfo(StudyArticleUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void updateViews() {
        this.views += 1;
    }
}
