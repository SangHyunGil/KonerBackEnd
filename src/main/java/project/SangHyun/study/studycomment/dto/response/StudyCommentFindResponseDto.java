package project.SangHyun.study.studycomment.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studycomment.domain.StudyComment;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 댓글 조회 요청 결과")
public class StudyCommentFindResponseDto {
    @ApiModelProperty(value = "스터디 댓글 ID(PK)")
    private Long commentId;

    @ApiModelProperty(value = "회원 닉네임")
    private MemberProfile memberProfile;

    @ApiModelProperty(value = "스터디 댓글 내용")
    private String content;

    @ApiModelProperty(value = "스터디 대댓글")
    List<StudyCommentFindResponseDto> children;

    public static StudyCommentFindResponseDto create(StudyComment studyComment) {
        MemberProfile memberProfile = studyComment.isDeleted() ? null : new MemberProfile(studyComment.getMember().getNickname(), studyComment.getMember().getProfileImgUrl());
        String content = studyComment.isDeleted() ? null : studyComment.getContent();
        return new StudyCommentFindResponseDto(studyComment.getId(),
                memberProfile,
                content,
                new ArrayList<>());
    }

    public static List<StudyCommentFindResponseDto> makeHierarchy(List<StudyComment> studyComments) {
        List<StudyCommentFindResponseDto> roots = new ArrayList<>();
        Map<Long, StudyCommentFindResponseDto> studyCommentMap = new HashMap<>();
        for (StudyComment studyComment : studyComments) {
            StudyCommentFindResponseDto commentDto = StudyCommentFindResponseDto.create(studyComment);
            studyCommentMap.put(commentDto.getCommentId(), commentDto);
            if (studyComment.getParent() == null) {
                StudyCommentFindResponseDto parentCommentDto = studyCommentMap.get(studyComment.getParent().getId());
                parentCommentDto.getChildren().add(commentDto);
            } else {
                roots.add(commentDto);
            }
        }

        return roots;
    }
}
