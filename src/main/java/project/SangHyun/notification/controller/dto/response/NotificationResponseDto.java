package project.SangHyun.notification.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.notification.domain.Notification;
import project.SangHyun.notification.service.dto.response.NotificationDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "알림 조회 결과")
public class NotificationResponseDto {

    @ApiModelProperty(value = "알림 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "알림 내용")
    private String content;

    @ApiModelProperty(value = "관련 URL")
    private String url;

    public static NotificationResponseDto create(NotificationDto notificationdto) {
        return new NotificationResponseDto(notificationdto.getId(), notificationdto.getContent(),
                notificationdto.getUrl());
    }
}
