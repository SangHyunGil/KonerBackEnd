package project.SangHyun.message.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.message.repository.impl.MessageCount;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "쪽지 상대 조회 결과")
public class CommunicatorFindResponseDto {
    @ApiModelProperty(value = "전송자 ID(PK)")
    private Long senderId;

    @ApiModelProperty(value = "수신자 ID(PK)")
    private Long receiverId;

    @ApiModelProperty(value = "쪽지 내용")
    private String content;

    @ApiModelProperty(value = "읽지 않은 쪽지 개수")
    private Long unReadCount;

    public static CommunicatorFindResponseDto create(MessageCount message) {
        return new CommunicatorFindResponseDto(message.getSender().getId(),
                message.getReceiver().getId(), message.getContent(), message.getUnReadCount());
    }
}
