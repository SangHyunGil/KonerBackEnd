package project.SangHyun.study.studyschedule.service.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.study.domain.Study;
import project.SangHyun.study.studyschedule.domain.StudySchedule;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyScheduleUpdateDto {

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

    public StudySchedule toEntity(Long studyId) {
        return StudySchedule.builder()
                .title(title)
                .startDate(start)
                .endDate(end)
                .startTime(startTime)
                .endTime(endTime)
                .study(new Study(studyId))
                .build();
    }
}
