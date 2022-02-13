package project.SangHyun.message.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.message.repository.impl.RecentMessageDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "쪽지 상대 조회 결과 서비스 계층 DTO")
public class FindCommunicatorsDto {

    @ApiModelProperty(value = "전송자 ID(PK)")
    private Long senderId;

    @ApiModelProperty(value = "수신자 ID(PK)")
    private Long receiverId;

    @ApiModelProperty(value = "쪽지 내용")
    private String content;

    @ApiModelProperty(value = "읽지 않은 쪽지 개수")
    private Long unReadCount;

    public static FindCommunicatorsDto create(RecentMessageDto recentMessageDto) {
        return new FindCommunicatorsDto(recentMessageDto.getSender().getId(), recentMessageDto.getReceiver().getId(),
                recentMessageDto.getContent(), recentMessageDto.getUnReadCount());
    }
}
