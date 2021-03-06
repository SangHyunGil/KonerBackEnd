package project.SangHyun.message.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.message.service.dto.request.MessageCreateDto;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "쪽지 전송 요청")
public class MessageCreateRequestDto {

    @ApiModelProperty(value = "전송자 ID(PK)", notes = "전송자 ID(PK)를 입력해주세요", required = true, example = "1L")
    @NotBlank(message = "전송자 ID(PK)를 입력해주세요.")
    private Long senderId;

    @ApiModelProperty(value = "수신자 ID(PK)", notes = "수신자 ID(PK)를 입력해주세요", required = true, example = "1L")
    @NotBlank(message = "수신자 ID(PK)를 입력해주세요.")
    private Long receiverId;

    @ApiModelProperty(value = "쪽지 내용", notes = "쪽지 내용을 입력해주세요", required = true, example = "내용")
    @NotBlank(message = "쪽지 내용을 입력해주세요.")
    private String content;

    public MessageCreateDto toServiceDto() {
        return new MessageCreateDto(senderId, receiverId, content);
    }
}
