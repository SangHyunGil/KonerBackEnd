package project.SangHyun.message.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.message.domain.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "쪽지 조회 결과")
public class MessageFindResponseDto {
    @ApiModelProperty(value = "전송자 ID(PK)")
    private Long senderId;

    @ApiModelProperty(value = "수신자 ID(PK)")
    private Long receiverId;

    @ApiModelProperty(value = "쪽지 내용")
    private String content;

    public static MessageFindResponseDto create(Message message) {
        return new MessageFindResponseDto(message.getSender().getId(),
                message.getReceiver().getId(), message.getContent());
    }
}
