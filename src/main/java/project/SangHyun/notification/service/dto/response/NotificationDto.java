package project.SangHyun.notification.service.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.notification.domain.Notification;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "알림 반환 서비스 계층 DTO")
public class NotificationDto {

    @ApiModelProperty(value = "알림 ID(PK)")
    private Long id;

    @ApiModelProperty(value = "알림 내용")
    private String content;

    @ApiModelProperty(value = "관련 URL")
    private String url;

    public static NotificationDto create(Notification notification) {
        return new NotificationDto(notification.getId(), notification.getContent(),
                notification.getUrl());
    }
}
