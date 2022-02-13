package project.SangHyun.study.studyschedule.controller.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyschedule.service.dto.request.StudyScheduleUpdateDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 스케줄 정보 업데이트 요청")
public class StudyScheduleUpdateRequestDto {

    @ApiModelProperty("스케줄 제목")
    private String title;

    @ApiModelProperty("스케줄 시작 날짜")
    private String start;

    @ApiModelProperty("스케줄 종료 날짜")
    private String end;

    @ApiModelProperty("스케줄 시작 시간")
    private String startTime;

    @ApiModelProperty("스케줄 종료 시간")
    private String endTime;

    public StudyScheduleUpdateDto toServiceDto() {
        return new StudyScheduleUpdateDto(title, start, end, startTime, endTime);
    }
}
