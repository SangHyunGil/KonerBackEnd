package project.SangHyun.study.videoroom.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.videoroom.service.dto.request.VideoRoomCreateDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("화상회의 방 생성 요청")
public class VideoRoomCreateRequestDto {

    @ApiModelProperty(name = "화상회의 방 요청 구분", value = "화상회의 방 요청을 위한 구분 문자열을 입력해주세요", example = "create")
    @NotBlank(message = "화상회의 요청 구분을 입력해주세요.")
    private String request;

    @ApiModelProperty(name = "화상회의 방 생성자 ID(PK)", value = "화상회의 방을 생성하는 회원의 ID를 입력해주세요", example = "1L")
    private Long memberId;

    @ApiModelProperty(name = "화상회의 방 제목", value = "화상회의 방 구분을 위한 제목을 입력해주세요.", example = "백엔드 화상회의 방")
    @NotBlank(message = "화상회의 방 제목을 입력해주세요.")
    @Size(min=2, message = "화상회의 방 제목이 너무 짧습니다.")
    private String title;

    @ApiModelProperty(name = "화상회의 방 비밀번호", value = "화상회의 방 입장을 위한 비밀번호를 입력해주세요.", example = "123")
    private String pin;

    public VideoRoomCreateDto toServiceDto() {
        return new VideoRoomCreateDto(request, memberId, title, pin);
    }
}
