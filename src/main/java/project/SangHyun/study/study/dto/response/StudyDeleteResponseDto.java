package project.SangHyun.study.study.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 삭제 요청 결과")
public class StudyDeleteResponseDto {
    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long studyId;

    @ApiModelProperty(value = "제목")
    private String title;

    public static StudyDeleteResponseDto create(Study study) {
        return new StudyDeleteResponseDto(study.getId(), study.getTitle());
    }
}
