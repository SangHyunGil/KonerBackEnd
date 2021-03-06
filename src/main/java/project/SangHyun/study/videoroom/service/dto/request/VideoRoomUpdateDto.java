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
@ApiModel("화상회의 방 수정 요청 서비스 계층 DTO")
public class VideoRoomUpdateDto {

    @ApiModelProperty(name = "화상회의 방 요청 구분")
    @NotBlank(message = "요청 구분을 입력해주세요.")
    private String request;

    @ApiModelProperty(name = "화상회의 방 제목")
    private String title;

    @ApiModelProperty(name = "화상회의 방 비밀번호")
    private String pin;
}
