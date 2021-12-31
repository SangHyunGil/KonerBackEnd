package project.SangHyun.study.study.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.SangHyun.study.study.domain.enums.RecruitState;
import project.SangHyun.study.study.domain.enums.StudyMethod;
import project.SangHyun.study.study.domain.enums.StudyState;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 정보 업데이트 요청")
public class StudyUpdateRequestDto {
    @ApiModelProperty(value = "스터디 제목", notes = "스터디 제목을 입력해주세요.", required = true, example = "백엔드 스터디")
    @NotBlank(message = "스터디 제목을 입력해주세요.")
    private String title;

    @ApiModelProperty(value = "스터디 주제", notes = "스터디 주제를 입력해주세요.", required = true, example = "백엔드")
    private List<String> tags;

    @ApiModelProperty(value = "스터디 내용", notes = "스터디 내용을 입력해주세요.", required = true, example = "내용")
    @NotBlank(message = "스터디 내용을 입력해주세요.")
    private String content;

    @ApiModelProperty(value = "스터디 시작 일정", notes = "스터디 시작 일정을 입력해주세요.", required = true, example = "2021-12-25")
    private String startDate;

    @ApiModelProperty(value = "스터디 종료 일정", notes = "스터디 종료 일정을 입력해주세요.", required = true, example = "2021-12-25")
    private String endDate;

    @ApiModelProperty(value = "스터디 학과", notes = "스터디 학과를 입력해주세요.", required = true, example = "컴퓨터공학과")
    private String department;

    @ApiModelProperty(value = "스터디 인원수", notes = "스터디 인원수를 입력해주세요.", required = true, example = "1")
    @Min(value = 1, message = "스터디 인원수는 1 이상이어야 합니다.")
    private Long headCount;

    @ApiModelProperty(value = "프로필 이미지", notes = "프로필 이미지를 업로드해주세요.", required = true, example = "")
    private MultipartFile profileImg;

    @ApiModelProperty(value = "스터디 방식", notes = "스터디 방식을 입력해주세요.", required = true, example = "대면")
    private StudyMethod studyMethod;

    @ApiModelProperty(value = "스터디 상태", notes = "스터디 상태를 입력해주세요.", required = true, example = "공부 중")
    private StudyState studyState;

    @ApiModelProperty(value = "스터디 모집 상태", notes = "스터디 모집 상태를 입력해주세요.", required = true, example = "모집 중")
    private RecruitState recruitState;
}
