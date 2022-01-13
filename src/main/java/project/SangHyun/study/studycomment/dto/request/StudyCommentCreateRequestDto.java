package project.SangHyun.study.studycomment.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.study.studyarticle.domain.StudyArticle;
import project.SangHyun.study.studycomment.domain.StudyComment;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 댓글 생성 요청")
public class StudyCommentCreateRequestDto {
    @ApiModelProperty(value = "스터디 댓글 생성자", notes = "스터디 댓글 생성자를 입력해주세요.", required = true, example = "1L")
    private Long memberId;

    @ApiModelProperty(value = "스터디 부모 댓글", notes = "스터디 부모 댓글을 입력해주세요.", required = true, example = "1L")
    private Long parentCommentId;

    @ApiModelProperty(value = "스터디 댓글 내용", notes = "스터디 댓글 내용 입력해주세요.", required = true, example = "테스트")
    private String content;

    public StudyComment toEntity(Long studyArticleId) {
        return StudyComment.builder()
                .member(new Member(memberId))
                .studyArticle(new StudyArticle(studyArticleId))
                .parent(getParentComment())
                .content(content)
                .isDeleted(false)
                .build();
    }

    private StudyComment getParentComment() {
        if (parentCommentId == null)
            return null;
        return new StudyComment(parentCommentId);
    }
}
