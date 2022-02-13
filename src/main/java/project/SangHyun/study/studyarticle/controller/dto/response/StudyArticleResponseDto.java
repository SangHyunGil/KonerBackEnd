package project.SangHyun.study.studyarticle.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.dto.MemberProfile;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 반환 DTO")
public class StudyArticleResponseDto {

    @ApiModelProperty(value = "스터디 게시글 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "스터디 게시글 작성자")
    private MemberProfile creator;

    @ApiModelProperty(value = "스터디 게시글 제목")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용")
    private String content;

    @ApiModelProperty(value = "스터디 게시글 조회수")
    private Long views;

    public static StudyArticleResponseDto create(StudyArticleDto studyArticleDto) {
        return new StudyArticleResponseDto(studyArticleDto.getId(), studyArticleDto.getCreator(),
                studyArticleDto.getTitle(), studyArticleDto.getContent(), studyArticleDto.getViews());
    }
}
