package project.SangHyun.study.studycomment.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studycomment.helper.HierarchyHelper;
import project.SangHyun.dto.response.MemberProfile;
import project.SangHyun.study.studycomment.domain.StudyComment;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 댓글 반환 서비스 계층 DTO")
public class StudyCommentDto {

    @ApiModelProperty(value = "스터디 댓글 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "스터디 댓글 작성자")
    private MemberProfile creator;

    @ApiModelProperty(value = "스터디 댓글 내용")
    private String content;

    @ApiModelProperty(value = "스터디 대댓글")
    List<StudyCommentDto> children;

    public static List<StudyCommentDto> create(List<StudyComment> studyComments) {
        HierarchyHelper hierarchyHelper = HierarchyHelper.of(studyComments, StudyCommentDto::create,
                StudyComment::getParent, StudyComment::getId, StudyCommentDto::getChildren);
        return hierarchyHelper.convertToHierarchyStructure();
    }

    public static StudyCommentDto create(StudyComment studyComment) {
        MemberProfile memberProfile = MemberProfile.create(studyComment);
        String content = studyComment.isDeleted() ? null : studyComment.getContent();
        return new StudyCommentDto(studyComment.getId(), memberProfile,
                content, new ArrayList<>());
    }
}
