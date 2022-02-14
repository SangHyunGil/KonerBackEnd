package project.SangHyun.study.videoroom.service.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.videoroom.domain.VideoRoom;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("화상회의 방 수정 요청 서비스 계층 DTO")
public class VideoRoomUpdateDto {

    @ApiModelProperty(name = "화상회의 방 요청 구분")
    @NotBlank(message = "요청 구분을 입력해주세요.")
    private String request;

    @ApiModelProperty(name = "화상회의 방 제목")
    @NotBlank(message = "방 제목을 입력해주세요.")
    @Size(min=2, message = "방 제목이 너무 짧습니다.")
    private String title;

    @ApiModelProperty(name = "화상회의 방 비밀번호")
    private String pin;
}
