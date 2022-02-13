package project.SangHyun.study.study.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.study.domain.StudyCategory;
import project.SangHyun.study.study.domain.StudyOptions.RecruitState;
import project.SangHyun.study.study.domain.StudyOptions.StudyMethod;
import project.SangHyun.study.study.domain.StudyOptions.StudyState;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 수정 요청 서비스 계층 DTO")
public class StudyUpdateDto {

    @ApiModelProperty(value = "스터디 제목")
    @NotBlank(message = "스터디 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "스터디 주제")
    private List<String> tags;

    @ApiModelProperty(value = "스터디 설명")
    private String description;

    @ApiModelProperty(value = "스터디 시작 일정")
    private String startDate;

    @ApiModelProperty(value = "스터디 종료 일정")
    private String endDate;

    @ApiModelProperty(value = "스터디 학과")
    private StudyCategory category;

    @ApiModelProperty(value = "스터디 인원수")
    private Long headCount;

    @ApiModelProperty(value = "프로필 이미지")
    private MultipartFile profileImg;

    @ApiModelProperty(value = "스터디 방식")
    private StudyMethod studyMethod;

    @ApiModelProperty(value = "스터디 상태")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태")
    private RecruitState recruitState;

    public Study toEntity() {
        return new Study(title, tags, description, startDate, endDate, category, headCount, studyMethod, studyState, recruitState);
    }
}
