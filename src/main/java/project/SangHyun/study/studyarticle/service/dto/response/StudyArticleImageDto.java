package project.SangHyun.study.studyarticle.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시글 이미지 업로드 반환 서비스 계층 DTO")
public class StudyArticleImageDto {

    @ApiModelProperty(value = "스터디 게시글 이미지 URL")
    private String imageUrl;
}
