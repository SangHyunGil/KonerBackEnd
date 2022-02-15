package project.SangHyun.study.videoroom.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.videoroom.service.dto.response.result.VideoRoomResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("화상회의 방 생성 결과 Janus Response DTO")
public class VideoRoomCreateResultDto {

    @ApiModelProperty(name = "Janus 성공 여부")
    private String janus;

    @ApiModelProperty(name = "Janus Transaction ID")
    private String transaction;

    @ApiModelProperty(name = "화상회의 방 요청 결과")
    private VideoRoomResult response;
}
