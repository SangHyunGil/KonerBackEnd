package project.SangHyun.study.studyarticle.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시글 이미지 업로드 요청 서비스 계층 DTO")
public class StudyArticleImageUploadDto {

    @ApiModelProperty(value = "이미지 파일")
    private List<MultipartFile> multipartFiles;
}
