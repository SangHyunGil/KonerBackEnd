package project.SangHyun.dto.response.study;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value = "스터디 참여 수 요청 결과")
public class StudyJoinCountResponseDto {
    @ApiModelProperty(value = "스터디 참여 수")
    private Long count;

    public static StudyJoinCountResponseDto createDto(Long count) {
        return StudyJoinCountResponseDto.builder()
                .count(count)
                .build();
    }

    @Builder
    public StudyJoinCountResponseDto(Long count) {
        this.count = count;
    }
}
