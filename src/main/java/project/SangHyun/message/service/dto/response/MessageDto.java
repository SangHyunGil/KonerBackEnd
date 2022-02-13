package project.SangHyun.message.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.message.domain.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "쪽지 조회 결과 서비스 계층 DTO")
public class MessageDto {

    @ApiModelProperty(value = "메세지 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "전송자 ID(PK)")
    private Long senderId;

    @ApiModelProperty(value = "수신자 ID(PK)")
    private Long receiverId;

    @ApiModelProperty(value = "쪽지 내용")
    private String content;

    public static MessageDto create(Message message) {
        return new MessageDto(message.getId(), message.getSender().getId(),
                message.getReceiver().getId(), message.getContent());
    }
}
