package project.SangHyun.message.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.message.service.dto.response.FindCommunicatorsDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "쪽지 상대 조회 결과")
public class FindCommunicatorsResponseDto {

    @ApiModelProperty(value = "전송자 ID(PK)")
    private Long senderId;

    @ApiModelProperty(value = "수신자 ID(PK)")
    private Long receiverId;

    @ApiModelProperty(value = "쪽지 내용")
    private String content;

    @ApiModelProperty(value = "읽지 않은 쪽지 개수")
    private Long unReadCount;

    public static FindCommunicatorsResponseDto create(FindCommunicatorsDto findCommunicatorsDto) {
        return new FindCommunicatorsResponseDto(findCommunicatorsDto.getSenderId(),
                findCommunicatorsDto.getReceiverId(), findCommunicatorsDto.getContent(), findCommunicatorsDto.getUnReadCount());
    }
}
