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
@ApiModel(value = "스터디 댓글 수정 요청 결과")
public class StudyCommentUpdateResponseDto {
    @ApiModelProperty(value = "스터디 댓글 생성자", notes = "스터디 댓글 생성자를 입력해주세요.", required = true, example = "1L")
    private Long memberId;

    @ApiModelProperty(value = "스터디 부모 댓글", notes = "스터디 부모 댓글을 입력해주세요.", required = true, example = "1L")
    private Long parentCommentId;

    @ApiModelProperty(value = "스터디 댓글 내용", notes = "스터디 댓글 내용 입력해주세요.", required = true, example = "테스트")
    private String content;

    public static StudyCommentUpdateResponseDto create(StudyComment comment) {
        return new StudyCommentUpdateResponseDto(comment.getId(), getParentComment(comment), comment.getContent());
    }

    private static Long getParentComment(StudyComment studyComment) {
        if (studyComment.getParent() == null)
            return null;
        return studyComment.getParent().getId();
    }
}
