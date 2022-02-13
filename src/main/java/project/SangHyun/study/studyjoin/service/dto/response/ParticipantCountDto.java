package project.SangHyun.study.studyjoin.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 참여 인원 조회 결과 서비스 계층 DTO")
public class ParticipantCountDto {

    @ApiModelProperty(value = "스터디 참여 수")
    private Long count;

    public static ParticipantCountDto create(Long count) {
        return new ParticipantCountDto(count);
    }
}
