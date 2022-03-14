package project.SangHyun.study.studyarticle.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.study.studyarticle.service.dto.request.StudyArticleImageUploadDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 게시글 생성 요청")
public class StudyArticleImageUploadRequestDto {

    @ApiModelProperty(value = "이미지 파일", notes = "이미지 파일을 업로드해주세요.", required = true)
    private List<MultipartFile> multipartFiles;

    public StudyArticleImageUploadDto toServiceDto() {
        return new StudyArticleImageUploadDto(multipartFiles);
    }
}
