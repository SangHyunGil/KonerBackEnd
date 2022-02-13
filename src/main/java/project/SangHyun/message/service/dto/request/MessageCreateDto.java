package project.SangHyun.message.service.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.member.domain.Member;
import project.SangHyun.message.domain.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateDto {

    @ApiModelProperty(value = "전송자 ID(PK)")
    private Long senderId;

    @ApiModelProperty(value = "수신자 ID(PK)")
    private Long receiverId;

    @ApiModelProperty(value = "쪽지 내용")
    private String content;

    public Message toEntity() {
        return Message.builder()
                .content(content)
                .sender(new Member(senderId))
                .receiver(new Member(receiverId))
                .deletedBySender(false)
                .deletedByReceiver(false)
                .isRead(false)
                .build();
    }
}
