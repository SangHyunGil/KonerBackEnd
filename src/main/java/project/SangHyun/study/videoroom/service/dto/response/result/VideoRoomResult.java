package project.SangHyun.study.videoroom.service.dto.response.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Janus Video Plugin Response DTO")
public class VideoRoomResult {

    @ApiModelProperty("화상회의 방 번호")
    private Long room;

    @ApiModelProperty("화상회의 방 제목")
    private String videoroom;
}
