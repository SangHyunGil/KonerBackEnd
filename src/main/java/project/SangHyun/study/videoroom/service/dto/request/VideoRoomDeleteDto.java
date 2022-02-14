package project.SangHyun.study.videoroom.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("화상회의 방 파괴 요청 서비스 계층 DTO")
public class VideoRoomDeleteDto {

    @ApiModelProperty(name = "화상회의 방 요청 구분")
    @NotBlank(message = "화상회의 요청 구분을 입력해주세요.")
    private String request;

    @ApiModelProperty(name = "화상회의 방 ID")
    @NotBlank(message = "화상회의 방 ID를 입력해주세요.")
    private Long room;

    public static VideoRoomDeleteDto create(Long roomId) {
        return new VideoRoomDeleteDto("destroy", roomId);
    }
}
