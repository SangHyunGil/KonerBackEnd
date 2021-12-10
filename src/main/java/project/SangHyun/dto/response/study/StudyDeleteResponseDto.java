package project.SangHyun.dto.response.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.domain.entity.Study;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 삭제 요청 결과")
public class StudyDeleteResponseDto {
    @ApiModelProperty(value = "스터디 ID(PK)")
    private Long studyId;

    @ApiModelProperty(value = "제목")
    private String title;

    public static StudyDeleteResponseDto createDto(Study study) {
        return StudyDeleteResponseDto.builder()
                .studyId(study.getId())
                .title(study.getTitle())
                .build();
    }

    @Builder
    public StudyDeleteResponseDto(Long studyId, String title) {
        this.studyId = studyId;
        this.title = title;
    }
}
