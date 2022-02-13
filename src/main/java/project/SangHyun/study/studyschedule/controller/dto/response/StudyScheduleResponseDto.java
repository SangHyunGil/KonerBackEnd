package project.SangHyun.study.studyschedule.controller.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SangHyun.study.studyschedule.service.dto.response.StudyScheduleDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "스터디 스케줄 생성 결과 서비스 계층 DTO")
public class StudyScheduleResponseDto {

    @ApiModelProperty("스케줄 ID(PK)")
    private Long id;

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

    public static StudyScheduleResponseDto create(StudyScheduleDto studyScheduleDto) {
        return new StudyScheduleResponseDto(studyScheduleDto.getId(), studyScheduleDto.getTitle(),
                studyScheduleDto.getStart(), studyScheduleDto.getEnd(),
                studyScheduleDto.getStartTime(), studyScheduleDto.getEndTime());
    }
}
