package project.SangHyun.study.studyjoin.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 참여 수 요청 결과")
public class ParticipantCountResponseDto {

    @ApiModelProperty(value = "스터디 참여 수")
    private Long count;

    public static ParticipantCountResponseDto create(Long count) {
        return new ParticipantCountResponseDto(count);
    }
}
