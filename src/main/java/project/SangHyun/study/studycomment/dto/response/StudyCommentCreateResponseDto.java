package project.SangHyun.study.studycomment.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studycomment.domain.StudyComment;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 댓글 생성 요청 결과")
public class StudyCommentCreateResponseDto {
    @ApiModelProperty(value = "스터디 댓글 ID(PK)")
    private Long commentId;

    @ApiModelProperty(value = "회원 ID(PK)")
    private Long memberId;

    @ApiModelProperty(value = "스터디 게시글 ID(PK)")
    private Long articleId;

    @ApiModelProperty(value = "스터디 댓글 내용")
    private String content;

    public static StudyCommentCreateResponseDto create(StudyComment studyComment) {
        return new StudyCommentCreateResponseDto(studyComment.getId(), studyComment.getMember().getId(),
                studyComment.getStudyArticle().getId(), studyComment.getContent());
    }
}
