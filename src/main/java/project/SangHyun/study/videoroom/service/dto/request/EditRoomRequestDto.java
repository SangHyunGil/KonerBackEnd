package project.SangHyun.study.videoroom.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditRoomRequestDto {
    @NotBlank(message = "요청 구분을 입력해주세요.")
    private String request;
    @NotBlank(message = "방 제목을 입력해주세요.")
    @Size(min=2, message = "방 제목이 너무 짧습니다.")
    private String description;
    private String pin;
}
