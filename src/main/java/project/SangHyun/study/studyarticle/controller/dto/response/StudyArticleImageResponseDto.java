package project.SangHyun.study.studyarticle.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyarticle.service.dto.response.StudyArticleImageDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 이미지 URL 반환 DTO")
public class StudyArticleImageResponseDto {

    @ApiModelProperty(value = "스터디 게시글 이미지 URL")
    private String imageUrl;

    public static StudyArticleImageResponseDto create(StudyArticleImageDto studyArticleImageDto) {
        return new StudyArticleImageResponseDto(studyArticleImageDto.getImageUrl());
    }
}
