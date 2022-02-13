package project.SangHyun.study.studycomment.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.dto.MemberProfile;
import project.SangHyun.study.studycomment.service.dto.response.StudyCommentDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 댓글 반환 DTO")
public class StudyCommentResponseDto {

    @ApiModelProperty(value = "스터디 댓글 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "스터디 댓글 작성자")
    private MemberProfile creator;

    @ApiModelProperty(value = "스터디 댓글 내용")
    private String content;

    @ApiModelProperty(value = "스터디 대댓글")
    List<StudyCommentDto> children;

    public static List<StudyCommentResponseDto> create(List<StudyCommentDto> studyCommentDto) {
        return studyCommentDto.stream()
                .map(StudyCommentResponseDto::create)
                .collect(Collectors.toList());
    }

    public static StudyCommentResponseDto create(StudyCommentDto studyCommentDto) {
        return new StudyCommentResponseDto(studyCommentDto.getId(), studyCommentDto.getCreator(),
                studyCommentDto.getContent(), studyCommentDto.getChildren());
    }
}
